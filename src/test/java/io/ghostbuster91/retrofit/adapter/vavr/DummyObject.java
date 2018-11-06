package io.ghostbuster91.retrofit.adapter.vavr;

import java.util.Objects;

public class DummyObject {
    private final String userId;

    public DummyObject(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DummyObject that = (DummyObject) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "DummyObject{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
