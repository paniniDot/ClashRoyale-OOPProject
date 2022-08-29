package model.entities.users;

/**
 * User level.
 */
public enum UserLevel {
  /**
   * Level 1.
   */
  LVL1("Level 1"),

  /**
   * Level 2.
   */
  LVL2("Level 2"),

  /**
   * Level 3.
   */
  LVL3("Level 3"),

  /**
   * Level 4.
   */
  LVL4("Level 4"),

  /**
   * Level 5.
   */
  LVL5("Level 5");

  private final String name;

  UserLevel(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
