package main;

public enum Ordre {
	PRED_NOPRED("Pred puis NoPred"),
	NOPRED_PRED("NoPred puis Pred");
	
	private String name;

    Ordre (String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
