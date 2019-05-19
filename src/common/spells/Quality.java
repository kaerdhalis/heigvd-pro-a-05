package common.spells;

public enum Quality {
    PERFECT, GOOD, OKAY;
    public int computePower(){
        switch (this){
            case PERFECT:
                return 10;
            case GOOD:
                return 5;
            case OKAY:
                return 3;
            default:
                return 0;
        }
    }
}
