package dc;

public enum ProviderId {
    FB("Facebook"),
    DUMMY("Dummy");

    private final String name;

    ProviderId(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
