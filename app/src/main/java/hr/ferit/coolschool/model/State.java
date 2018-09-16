package hr.ferit.coolschool.model;

public enum State {
    BBZ("Bjelovarsko-bilogorska"),
    BPZ("Brodsko-posavska"),
    DNZ("Dubrovačko-neretvanska"),
    IZ("Istarska"),
    KZ("Karlovačka"),
    KKZ("Koprivničko-križevačka"),
    KZZ("Krapinsko-zagorska"),
    LSZ("Ličko-senjska"),
    MZ("Međimurska"),
    OBZ("Osječko-baranjska"),
    PSZ("Požeško-slavonska"),
    PGZ("Primorsko-goranska"),
    SMZ("Sisačko-moslavačka"),
    SDZ("Splitsko-dalmatinska"),
    SKZ("Šibensko-kninska"),
    VZ("Varaždinska"),
    VPZ("Virovitičko-podravska"),
    VSZ("Vukovarsko-srijemska"),
    ZDZ("Zadarska"),
    ZGZ("Zagrebačka"),
    GZG("Grad Zagreb");

    private final String name;

    State(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
