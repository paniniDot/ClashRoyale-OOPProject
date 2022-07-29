package model.utilities.inGameUtilities;

import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import com.badlogic.gdx.math.Vector2;

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
    final var obstacles = this.getObstacles();
    int x = X_START;
    int y = Y_START;
    for (int i = 1; i < HORIZONTAL_UNITS; i++) {
      y = Y_START;
      for (int j = 1; j < VERTICAL_UNITS; j++) {
        final var coords = new Vector2(i, j);
        if (!obstacles.contains(coords)) {
          final var vertex = new MapUnit(coords, new Vector2(x, y), MapUnit.Type.TERRAIN);
          System.out.println(vertex);
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
          final var mapUnit = new MapUnit(new Vector2(i, j), new Vector2(X_START + MapUnit.WIDTH * (i - 1), Y_START + MapUnit.HEIGHT * (j - 1)), MapUnit.Type.TERRAIN);
          if (this.map.containsVertex(mapUnit) && !vertex.equals(mapUnit)) {
            this.map.addEdge(vertex, mapUnit);
          } 
        }
      }
      }
    }
  }

  private List<Vector2> getObstacles() {
    return List.of(
        new Vector2(1,15), 
        new Vector2(2,15), 
        new Vector2(3,15), 
        new Vector2(1,16), 
        new Vector2(2,16), 
        new Vector2(3,16),

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

        new Vector2(16,15), 
        new Vector2(17,15), 
        new Vector2(18,15),
        new Vector2(16,16), 
        new Vector2(17,16), 
        new Vector2(18,16),

        new Vector2(3,5),
        new Vector2(3,6),
        new Vector2(3,7),
        new Vector2(4,5),
        new Vector2(4,6),
        new Vector2(4,7),
        new Vector2(5,5),
        new Vector2(5,6),
        new Vector2(5,7),

        new Vector2(8,1),
        new Vector2(8,2),
        new Vector2(8,3),
        new Vector2(9,1),
        new Vector2(9,2),
        new Vector2(9,3),
        new Vector2(10,1),
        new Vector2(10,2),
        new Vector2(10,3),

        new Vector2(14,5),
        new Vector2(14,6),
        new Vector2(14,7),
        new Vector2(15,5),
        new Vector2(15,6),
        new Vector2(15,7),
        new Vector2(16,5),
        new Vector2(16,6),
        new Vector2(16,7),

        new Vector2(3,24),
        new Vector2(3,25),
        new Vector2(3,26),
        new Vector2(4,24),
        new Vector2(4,25),
        new Vector2(4,26),
        new Vector2(5,24),
        new Vector2(5,25),
        new Vector2(5,26), 

        new Vector2(8,27),
        new Vector2(8,28),
        new Vector2(8,29),
        new Vector2(9,27),
        new Vector2(9,28),
        new Vector2(9,29),
        new Vector2(10,27),
        new Vector2(10,28),
        new Vector2(10,29),

        new Vector2(14,24),
        new Vector2(14,25),
        new Vector2(14,26),
        new Vector2(15,24),
        new Vector2(15,25),
        new Vector2(15,26),
        new Vector2(16,24),
        new Vector2(16,25),
        new Vector2(16,26)
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
  public List<Vector2> getPath(final Vector2 source, final Vector2 dest) {
    System.out.println("source: " + source + ", dest: " + dest + "" + getMapUnitFromPixels(new Vector2(240,483)));
    if (this.map.containsVertex(getMapUnitFromPixels(source)) && this.map.containsVertex(getMapUnitFromPixels(dest))) {
      return new AStarShortestPath<MapUnit, DefaultEdge>(this.map, (src, dst) -> this.euclideanDistance(src.getCoordinates(), dst.getCoordinates()))
          .getPath(this.getMapUnitFromPixels(source), this.getMapUnitFromPixels(dest))
          .getVertexList()
          .stream()
          .map(MapUnit::getCenter)
          .collect(Collectors.toList());
    }
    return List.of();
  }

  /**
   * 
   * @return the map.
   */
  public Graph<MapUnit, DefaultEdge> getMap() {
    return this.map;
  }

  private MapUnit getMapUnitFromPixels(final Vector2 pixels) {
    final var coords = new Vector2((float) Math.ceil((pixels.x - X_START) / MapUnit.WIDTH), (float) Math.ceil((pixels.y - Y_START) / MapUnit.HEIGHT));
    //System.out.println(pixels + "-> " + coords);
    // il pixel passato alla funzione � un pixel a caso all'interno del rettangolo, io ho bisogno di quello in basso a sx per creare il rettangolo, uso il metodo sotto
    final var mapUnit = new MapUnit(coords, this.getPixelsFromUnitCoords(coords), MapUnit.Type.TERRAIN);
    //System.out.println(mapUnit);
    return mapUnit;
  }

  private Vector2 getPixelsFromUnitCoords(final Vector2 coords) {
    return new Vector2((coords.x - 1) * MapUnit.WIDTH + X_START, (coords.y - 1) * MapUnit.HEIGHT + Y_START);
  }

  private double euclideanDistance(final Vector2 src, final Vector2 dst) {
    return Math.sqrt(Math.pow(src.x - dst.x, 2) + Math.pow(src.y - dst.y, 2));
  }
}
