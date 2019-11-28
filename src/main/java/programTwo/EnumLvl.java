package programTwo;

public enum EnumLvl implements ChoicePicture {
    ULTRA_HARD("ultra hard",1403,353,18,94,120),
    SUPER_HARD("super hard",1403,522,231, 3, 2),
    VERY_HARD("very hard",1403,690,254, 59, 0),
    HARD("hard",1403,855,255, 119, 3),
    NORMAL("normal",1403,855,135, 153, 41);

    private String NAME;
    private int X_POINT_POINTER;
    private int Y_POINT_POINTER;
    private int R_COLOR;
    private int G_COLOR;
    private int B_COLOR;

    EnumLvl(String NAME, int X_POINT_POINTER, int Y_POINT_POINTER, int R_COLOR, int G_COLOR, int B_COLOR){
        this.NAME = NAME;
        this.X_POINT_POINTER = X_POINT_POINTER;
        this.Y_POINT_POINTER = Y_POINT_POINTER;
        this.R_COLOR = R_COLOR;
        this.G_COLOR = G_COLOR;
        this.B_COLOR = B_COLOR;
    }

    @Override
    public String getNAME() {
        return NAME;
    }

    @Override
    public int getX_POINT_POINTER() {
        return X_POINT_POINTER;
    }

    @Override
    public int getY_POINT_POINTER() {
        return Y_POINT_POINTER;
    }

    @Override
    public int getR_COLOR() {
        return R_COLOR;
    }

    @Override
    public int getG_COLOR() {
        return G_COLOR;
    }

    @Override
    public int getB_COLOR() {
        return B_COLOR;
    }
}
