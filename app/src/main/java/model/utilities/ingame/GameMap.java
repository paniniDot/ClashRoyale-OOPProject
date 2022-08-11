package model.utilities.ingame;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import com.badlogic.gdx.math.Vector2;

import model.actors.Attackable;
import model.actors.cards.troops.Wizard;
import model.utilities.Pair;
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

    this.addVerteces();
    this.addEdges();
  }

  private void addVerteces() {
    int x = X_START;
    int y = Y_START;
    for (int i = 1; i < HORIZONTAL_UNITS; i++) {
      y = Y_START;
      for (int j = 1; j < VERTICAL_UNITS; j++) {
        final var coords = new Vector2(i, j);
        if (!this.getObstacles().contains(coords)) {
          final var vertex = new MapUnit(coords, new Vector2(x, y), this.getTowers().contains(coords) ? MapUnit.Type.TOWER : MapUnit.Type.TERRAIN);
          // System.out.println(vertex);
          this.map.addVertex(vertex);
        }
        y += MapUnit.HEIGHT;
      }
      x += MapUnit.WIDTH;
    }
  }

  private void addEdges() {
    for (final var vertex : this.map.vertexSet()) {
      if (vertex.getCoordinates().x > 1 && vertex.getCoordinates().y > 1) {
      for (var i = vertex.getCoordinates().x - 1; i <= vertex.getCoordinates().x + 1; i++) {
        for (var j = vertex.getCoordinates().y - 1; j <= vertex.getCoordinates().y + 1; j++) {
          if (!this.getObstacles().contains(new Vector2(i, j))) {
          final var mapUnit = new MapUnit(new Vector2(i, j), new Vector2(X_START + MapUnit.WIDTH * (i - 1), Y_START + MapUnit.HEIGHT * (j - 1)), this.getTowers().contains(new Vector2(i, j)) ? MapUnit.Type.TOWER : MapUnit.Type.TERRAIN);
          if (this.map.containsVertex(mapUnit) && !vertex.equals(mapUnit)) {
            this.map.addEdge(vertex, mapUnit);
          }
          }
        }
      }
      }
    }
  }

  public List<Vector2> getTowers() {
    return List.of(
        new Vector2(4,6),
        new Vector2(4,7),
        new Vector2(4,8),
        new Vector2(4,9),
        new Vector2(9,1),
        new Vector2(9,2),
        new Vector2(9,3),
        new Vector2(9,4),
        new Vector2(9,5),
        new Vector2(9,6),

        new Vector2(10,1),
        new Vector2(10,2),
        new Vector2(10,3),
        new Vector2(10,4),
        new Vector2(10,5),
        new Vector2(10,6),

        new Vector2(15,6),
        new Vector2(15,7),
        new Vector2(15,8),
        new Vector2(15,9),

        new Vector2(4,25),
        new Vector2(4,26),
        new Vector2(4,27),

        new Vector2(9,28),
        new Vector2(9,29),
        new Vector2(9,30),
        new Vector2(9,31),
        new Vector2(10,28),
        new Vector2(10,29),
        new Vector2(10,30),
        new Vector2(10,31),

        new Vector2(15,25),
        new Vector2(15,26),
        new Vector2(15,27)


        );
  }

  public List<Vector2> getObstacles() {
    return List.of(
        new Vector2(3,5),
        new Vector2(3,6),
        new Vector2(3,7),
        new Vector2(3,8),
        new Vector2(3,9),
        new Vector2(4,5),
        new Vector2(5,5),
        new Vector2(5,6),
        new Vector2(5,7),
        new Vector2(5,8),
        new Vector2(5,9),

        new Vector2(8,1),
        new Vector2(8,2),
        new Vector2(8,3),
        new Vector2(8,4),
        new Vector2(8,5),
        new Vector2(8,6),

        new Vector2(11,1),
        new Vector2(11,2),
        new Vector2(11,3),
        new Vector2(11,4),
        new Vector2(11,5),
        new Vector2(11,6),

        new Vector2(3,25),
        new Vector2(3,26),
        new Vector2(3,27),
        new Vector2(3,28),
        new Vector2(4,28),
        new Vector2(5,25),
        new Vector2(5,26), 
        new Vector2(5,27),
        new Vector2(5,28),
        new Vector2(8,28),
        new Vector2(8,29),
        new Vector2(8,30),
        new Vector2(8,31),
        new Vector2(11,28),
        new Vector2(11,29),
        new Vector2(11,30),
        new Vector2(11,31),
        new Vector2(14,25),
        new Vector2(14,26),
        new Vector2(14,27),
        new Vector2(14,28),
        new Vector2(15,28),
        new Vector2(16,25),
        new Vector2(16,26), 
        new Vector2(16,27),
        new Vector2(16,28),

        new Vector2(14,5),
        new Vector2(14,6),
        new Vector2(14,7),
        new Vector2(14,8),
        new Vector2(14,9),
        new Vector2(15,5),
        new Vector2(16,5),
        new Vector2(16,6),
        new Vector2(16,7),
        new Vector2(16,8),
        new Vector2(16,9),

        new Vector2(1,15), 
        new Vector2(2,15), 
        new Vector2(3,15), 
        new Vector2(1,16), 
        new Vector2(2,16), 
        new Vector2(3,16),
        new Vector2(1,17), 
        new Vector2(2,17), 
        new Vector2(3,17),

        new Vector2(5,15), 
        new Vector2(6,15), 
        new Vector2(7,15),
        new Vector2(8,15), 
        new Vector2(9,15), 
        new Vector2(10,15),
        new Vector2(11,15), 
        new Vector2(12,15), 
        new Vector2(13,15),
        new Vector2(14,15), 
        new Vector2(5,16), 
        new Vector2(6,16), 
        new Vector2(7,16),
        new Vector2(8,16), 
        new Vector2(9,16), 
        new Vector2(10,16),
        new Vector2(11,16), 
        new Vector2(12,16), 
        new Vector2(13,16),
        new Vector2(14,16),
        new Vector2(5,17), 
        new Vector2(6,17), 
        new Vector2(7,17),
        new Vector2(8,17), 
        new Vector2(9,17), 
        new Vector2(10,17),
        new Vector2(11,17), 
        new Vector2(12,17), 
        new Vector2(13,17),
        new Vector2(14,17),

        new Vector2(16,15), 
        new Vector2(17,15), 
        new Vector2(18,15),
        new Vector2(16,16), 
        new Vector2(17,16), 
        new Vector2(18,16),
        new Vector2(16,17), 
        new Vector2(17,17), 
        new Vector2(18,17)
        );
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
          .filter(el -> !this.getTowers().contains(el.getCoordinates()))
          .map(MapUnit::getCenter)
          .collect(Collectors.toList());
    }
    return List.of();
  }

  /**
   * find enemy method.
   * @param source
   * @param destination
   * @return List<Pair<Pair<Card, Card>, List<Vector2>>> 
   */
  public List<Pair<Pair<Attackable, Attackable>, List<Vector2>>> findEnemy(final List<Attackable> source, final List<Attackable> destination) {
    final List<Pair<Pair<Attackable, Attackable>, List<Vector2>>> cardPaths = new ArrayList<>();
    Attackable dest = null;
    for (final Attackable src : source) {
      double min = Double.MAX_VALUE;
      for (final Attackable dst : destination) {
        final double distance = VectorsUtilities.euclideanDistance(src.getPosition(), dst.getPosition());
        if (Double.compare(min, distance) > 0) {
          dest = dst;
          min = distance;
        }
      }
      src.setCurrentTarget(dest);
      cardPaths.add(new Pair<Pair<Attackable, Attackable>, List<Vector2>>(new Pair<Attackable, Attackable>(src, dest), this.getPath(this.getMapUnitFromPosition(src.getPosition()), this.getMapUnitFromPosition(dest.getPosition()))));
    }
    //System.out.println(cardPaths.stream().filter(p -> p.getX().getX().getClass().equals(Wizard.class)).map(p -> p + " POSIZIONE ATTORE : " + p.getX().getX().getPosition()).collect(Collectors.toList()));
    System.out.println(cardPaths);
    return cardPaths;
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
    return new MapUnit(coords, this.getPixelsFromUnitCoords(coords), this.getTowers().contains(coords) ? MapUnit.Type.TOWER : MapUnit.Type.TERRAIN);
  }

  private Vector2 getPixelsFromUnitCoords(final Vector2 coords) {
    return new Vector2((coords.x - 1) * MapUnit.WIDTH + X_START, (coords.y - 1) * MapUnit.HEIGHT + Y_START);
  }

}
