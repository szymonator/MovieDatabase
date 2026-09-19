package utils;

import java.awt.*;

public final class Constants {
    public static final int hSize = 1280;
    public static final int vSize = 720;

    public static enum MovieDatabaseColor {AUBERGINE, TEAL, BLUE, ORANGE, RUBY, GOLD};

    private static MovieDatabaseColor movieDatabaseColor = MovieDatabaseColor.RUBY;

    public static Color getBackground() {
        switch(movieDatabaseColor) { 
            case TEAL: return new Color(80, 127, 112);
            case BLUE: return new Color(65, 116, 141);
            case ORANGE: return new Color(190, 83, 28);
            case RUBY: return new Color(157, 34, 53);
            case GOLD: return new Color(214, 154, 45);
            default: return new Color(60, 16, 83);
        }
    }

    public static Color getFontColor() {
        return Color.WHITE;
    }

    public static Color getHighlight() {
        switch (movieDatabaseColor) {
            case TEAL: return new Color(133, 165, 155);
            case BLUE: return new Color(122, 158, 175);
            case ORANGE: return new Color(210, 135, 35);
            case RUBY: return new Color(186, 101, 113);
            case GOLD: return new Color(227, 184, 108);
            default: return new Color(119, 88, 135);
        }
    }

    public static String getIcon() {
        switch (movieDatabaseColor) {
            case TEAL: return "src/main/resources/img/movieDatabaseIcon_teal.png";
            case BLUE: return "src/main/resources/img/movieDatabaseIcon_blue.png";
            case ORANGE: return"src/main/resources/img/movieDatabaseIcon_orange.png";
            case RUBY: return "src/main/resources/img/movieDatabaseIcon_ruby.png";
            case GOLD: return "src/main/resources/img/movieDatabaseIcon_gold.png";
            default: return"src/main/resources/img/movieDatabaseIcon_aubergine.png";
        }
    }

    public static final int mostUserRatingCount = 100;
    public static final int topMoviesCount = 100;

    public static final String defaultCreditsPath       = "data/credits.csv";
    public static final String defaultKeywordsPath      = "data/keywords.csv";
    public static final String defaultMovieMetadataPath = "data/movies_metadata.csv";
    public static final String defaultRatingsPath       = "data/ratings.csv";

}
