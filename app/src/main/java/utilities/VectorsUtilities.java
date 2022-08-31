package utilities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Utilities for vectors.
 */
public final class VectorsUtilities {

  private VectorsUtilities() {
  }

  /**
   * LibGDX uses a coordinates system which has y-axis 0 placed on the left top corner of the screen.
   * This utility convert it back to the usual system.
   * @param cam
   *          {@inheritDoc}
   * @param x
   *          x coordinate
   * @param y
   *          y coordinate
   * @return the unprojected vector.
   */
  public static Vector2 unproject2DVector(final Camera cam, final float x, final float y) {
    final var vec3d = cam.unproject(new Vector3(x, y, 0));
    return new Vector2(vec3d.x, vec3d.y);
  }

  /**
   * Evaluate the distance between two points.
   * 
   * @param src
   *          the (x,y) start point.
   * @param dst
   *          the (x,y) destination point.
   * @return the distance between the two points
   */
  public static double euclideanDistance(final Vector2 src, final Vector2 dst) {
    return src.dst(dst);
  }
}
