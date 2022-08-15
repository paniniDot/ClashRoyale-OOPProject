package model.utilities.ingame;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import model.actors.Attackable;
import model.utilities.VectorsUtilities;

/**
 * The actual map.
 */
public class GameMap {
  private static final int HORIZONTAL_UNITS = 19;
  private static final int VERTICAL_UNITS = 32;
  private static final int X_START = 175;
  private static final int Y_START = 255;

  private final Graph<MapUnit, DefaultEdge> map;
  private final List<Vector2> obstaclePositions;
  private final List<Vector2> towerPositions;
  /**
   * Constructor.
   */
  public GameMap() {
    this.map = GraphTypeBuilder
        .<MapUnit, DefaultEdge>undirected()
        .allowingMultipleEdges(false)
        .allowingSelfLoops(false)
        .weighted(false)
        .edgeClass(DefaultEdge.class)
        .buildGraph();
    this.towerPositions = this.getTowers();
    this.obstaclePositions = this.getObstacles();
    this.addVerteces();
    this.addEdges();
  }

  private void addVerteces() {
    int x = X_START;
    int y;
    for (int i = 1; i < HORIZONTAL_UNITS; i++) {
      y = Y_START;
      for (int j = 1; j < VERTICAL_UNITS; j++) {
        final var coords = new Vector2(i, j);
        if (!this.obstaclePositions.contains(coords)) {
          final var vertex = new MapUnit(coords, new Vector2(x, y), this.towerPositions.contains(coords) ? MapUnit.Type.TOWER : MapUnit.Type.TERRAIN);
          this.map.addVertex(vertex);
        }
        y += MapUnit.HEIGHT;
      }
      x += MapUnit.WIDTH;
    }
  }

  private void addEdges() {
    this.map.vertexSet().stream()
    .filter(src -> this.checkForOutOfBounds(src.getCoordinates()))
    .forEach(src -> Stream.iterate(src.getCoordinates().x - 1, n -> n <= src.getCoordinates().x + 1, n -> n + 1)
        .forEach(i -> Stream.iterate(src.getCoordinates().y - 1, n -> n <= src.getCoordinates().y + 1, n -> n + 1)
            .forEach(j -> Stream.of(new Vector2(i, j))
                .filter(v -> !this.obstaclePositions.contains(v))
                .map(v -> new MapUnit(v, this.getPixelsFromUnitCoords(v), this.towerPositions.contains(v) ? MapUnit.Type.TOWER : MapUnit.Type.TERRAIN))
                .filter(dst -> this.map.containsVertex(dst) && !src.equals(dst))
                .forEach(dst -> this.map.addEdge(src, dst)))));
  }

  private boolean checkForOutOfBounds(final Vector2 pos) {
    return pos.x > 1 && pos.y > 1;
  }

  @SuppressWarnings("serial")
  private List<Vector2> getTowers() {
    try {
      return new Gson().fromJson(new FileReader(Gdx.files.internal("saves/Arena1Resources/towers.json").file()), new TypeToken<List<Vector2>>() { }.getType());
    } catch (IOException e) {
      return Collections.emptyList();
    }
  }

  @SuppressWarnings("serial")
  private List<Vector2> getObstacles() {
    try {
      return new Gson().fromJson(new FileReader(Gdx.files.internal("saves/Arena1Resources/obstacles.json").file()), new TypeToken<List<Vector2>>() { }.getType());
    } catch (IOException e) {
      return Collections.emptyList();
    }
  }

  /**
   * Evaluate the best path form a source to a destination.
   * @param source
   *            the source point of the path.
   * @param dest
   *            the destination point.
   * @return a list of vector2 of mapUnits coordinates.
   */
  private List<Vector2> getPath(final MapUnit source, final MapUnit dest) {
    if (this.map.containsVertex(source) && this.map.containsVertex(dest)) {
      return new AStarShortestPath<MapUnit, DefaultEdge>(this.map, (src, dst) -> VectorsUtilities.euclideanDistance(src.getCenter(), dst.getCenter()))
          .getPath(source, dest)
          .getVertexList()
          .stream()
          .filter(el -> !this.towerPositions.contains(el.getCoordinates()))
          .map(MapUnit::getCenter)
          .collect(Collectors.toList());
    }
    return List.of();
  }

  /**
   * Get the next position {@link Vector2} in the path of an attackable, choosing the destinations in a list passed as argument.
   * @param source
   *              the {@link Attackable} entity to find the next position of.
   * @param destinations
   *              a {@link List} of possible destinations for the source.
   * @return
   *              the next {@link Vector2} inside the path of the source.
   */
  public Vector2 getNextPosition(final Attackable source, final List<Attackable> destinations) {
    Attackable dest = null;
    double min = Double.MAX_VALUE;
    for (final var dst : destinations) {
      final double distance = VectorsUtilities.euclideanDistance(source.getPosition(), dst.getPosition());
      if (Double.compare(min, distance) > 0) {
        dest = dst;
        min = distance;
      }
    }
    final var path = this.getPath(this.getMapUnitFromPosition(source.getPosition()), this.getMapUnitFromPosition(dest.getPosition()));
    return path.size() > 1 ? path.get(1) : path.get(0);
  }

  /**
   * 
   * @param position
   *            the {@link Vector2} to be checked if inside the map or not.
   * @return whether the position is contained or not in the map.
   */
  public boolean containsPosition(final Vector2 position) {
    return this.map.containsVertex(this.getMapUnitFromPosition(position));
  }

  private MapUnit getMapUnitFromPosition(final Vector2 pixels) {
    final var coords = new Vector2((float) Math.ceil((pixels.x - X_START) / MapUnit.WIDTH), (float) Math.ceil((pixels.y - Y_START) / MapUnit.HEIGHT));
    return new MapUnit(coords, this.getPixelsFromUnitCoords(coords), this.towerPositions.contains(coords) ? MapUnit.Type.TOWER : MapUnit.Type.TERRAIN);
  }

  private Vector2 getPixelsFromUnitCoords(final Vector2 coords) {
    return new Vector2((coords.x - 1) * MapUnit.WIDTH + X_START, (coords.y - 1) * MapUnit.HEIGHT + Y_START);
  }

}
