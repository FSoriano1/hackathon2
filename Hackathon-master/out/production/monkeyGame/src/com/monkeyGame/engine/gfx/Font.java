package com.monkeyGame.engine.gfx;

public class Font {

    public static final Font TEST = new Font("/font.png");
    public static final Font STANDARD = new Font("/resources/text_ss1.png");


    private Image fontImage;
    private int[] offsets;
    private int[] widths;

    public Font(String path){
        fontImage = new Image(path);
        offsets = new int[59];
        widths = new int[59];

        int unicode = 0;

        for(int i=0; i<fontImage.getW(); i++){
            if(fontImage.getP()[i] == 0xff0000ff){ //find blue
                offsets[unicode]=i;
            }
            if(fontImage.getP()[i] == 0xffffff00){ //find widths, add widths
                widths[unicode]= i-offsets[unicode];
                unicode++;
            }
        }
    }

    public Image getFontImage() {
        return fontImage;
    }

    public int[] getWidths() {
        return widths;
    }

    public void setWidths(int[] widths) {
        this.widths = widths;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public void setOffsets(int[] offsets) {
        this.offsets = offsets;
    }
}
