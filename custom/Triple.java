package org.usfirst.frc4904.standard.custom;

public class Triple<A, B, C> {
  private final A m_first;
  private final B m_second;
  private final C m_third;

  public Triple(A first, B second, C third) {
    m_first = first;
    m_second = second;
    m_third = third;
  }

  public A getFirst() {
    return m_first;
  }

  public B getSecond() {
    return m_second;
  }

  public C getThird() {
    return m_third;
  }

  public static <A, B, C> Triple<A, B, C> of(A a, B b, C c) {
    return new Triple<>(a, b, c);
  }
}
