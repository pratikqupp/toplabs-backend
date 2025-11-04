package com.TopLabBazaar2909.TLBnew2909.embedded;
public enum SampleTubeType {
    RED_TOP("Red", "No additive - used for serum collection"),
    YELLOW_TOP("Yellow", "Contains SPS or ACD - used for blood cultures or genetic studies"),
    BLUE_TOP("Light Blue", "Contains sodium citrate - used for coagulation tests"),
    GREEN_TOP("Green", "Contains heparin - used for plasma determinations in chemistry"),
    LAVENDER_TOP("Lavender", "Contains EDTA - used for hematology tests like CBC"),
    GRAY_TOP("Gray", "Contains fluoride and oxalate - used for glucose and lactate testing"),
    GOLD_TOP("Gold", "SST (Serum Separator Tube) - used for chemistry panels"),
    PINK_TOP("Pink", "Contains EDTA - used for blood bank testing"),
    BLACK_TOP("Black", "Contains sodium citrate - used for ESR (erythrocyte sedimentation rate)"),
    ORANGE_TOP("Orange", "Contains thrombin - used for STAT serum testing");

    private final String color;
    private final String description;

    SampleTubeType(String color, String description) {
        this.color = color;
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }
}
