import java.time.LocalDate;
import java.io.Serializable;

public class LifeCycle implements Serializable {
    final protected String NUTRIENT_LINE;
    final protected String NAME;
    final protected String STRAIN;
    final protected boolean IS_PHOTOPERIOD;
    //stage holds data for whether or not the lifecycle is in 1)seedling, 2)vegetative, or 3)flowering period.
    protected int stage;
    final protected LocalDate TIMESTAMP;
    protected LocalDate bloomStamp = null;
    //int week;
    //Constructor
    public LifeCycle(String name, String strain, boolean isPhotoPeriod) {
        this.NAME = name;
        this.STRAIN = strain;
        this.IS_PHOTOPERIOD = isPhotoPeriod;
        this.NUTRIENT_LINE = "General Hydroponics";
        stage = 1;
        TIMESTAMP = LocalDate.now();
    }

    public String getName() {
        return NAME;
    }

    public String getStrain() {
        return STRAIN;
    }

    public String getNutrientLine() {
        return NUTRIENT_LINE;
    }

    public String getCycleType() {
        if (IS_PHOTOPERIOD) {
            return "Photoperiod";
        }
        else {
            return "Autoflowering";
        }
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getPhase() {
        if (stage == 1) {
            if (LocalDate.now().compareTo(TIMESTAMP) >= 7) {
                stage = 2;
                return "Vegetative";
            }
            return "Seedling";
        }
        else if (stage == 2) {
            return "Vegetative";
        }
        else if (stage == 3) {
            return "Flower";
        }
        else {
            return "Harvested";
        }
    }

    public int getDaysElapsed() {
        return LocalDate.now().compareTo(TIMESTAMP);
    }

    public void bloom() {
        stage = 3;
        bloomStamp = LocalDate.now();
    }

    public int getDaysElapsedSinceBloom() {
        return LocalDate.now().compareTo(bloomStamp);
    }

}
