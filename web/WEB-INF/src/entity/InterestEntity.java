package entity;

/**
 * Created by Administrator on 2017/7/12.
 */
public class InterestEntity {
    private String userid;
    private String music;
    private String sport;
    private String book;
    private String movie;
    private String game;
    private String other;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InterestEntity that = (InterestEntity) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (music != null ? !music.equals(that.music) : that.music != null) return false;
        if (sport != null ? !sport.equals(that.sport) : that.sport != null) return false;
        if (book != null ? !book.equals(that.book) : that.book != null) return false;
        if (movie != null ? !movie.equals(that.movie) : that.movie != null) return false;
        if (game != null ? !game.equals(that.game) : that.game != null) return false;
        return other != null ? other.equals(that.other) : that.other == null;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (music != null ? music.hashCode() : 0);
        result = 31 * result + (sport != null ? sport.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (movie != null ? movie.hashCode() : 0);
        result = 31 * result + (game != null ? game.hashCode() : 0);
        result = 31 * result + (other != null ? other.hashCode() : 0);
        return result;
    }
}
