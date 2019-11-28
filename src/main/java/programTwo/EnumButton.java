package programTwo;

public enum EnumButton implements ChoicePicture {
    GO("go",1633,954,244,127,13),
    END_GAME("end game",1633, 954,33,65,60),
    BATTLE_AGAIN("battle again",1758,952,245,128,14),
    FINISH("finish",1517,948,97,149,106);

    private String NAME;
    private int X_POINT_POINTER;
    private int Y_POINT_POINTER;
    private int R_COLOR;
    private int G_COLOR;
    private int B_COLOR;

    EnumButton(String NAME, int X_POINT_POINTER, int Y_POINT_POINTER, int R_COLOR, int G_COLOR, int B_COLOR){
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
