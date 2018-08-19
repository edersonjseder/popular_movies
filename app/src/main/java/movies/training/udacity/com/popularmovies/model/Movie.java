package movies.training.udacity.com.popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "movie")
public class Movie implements Serializable {

    @JsonProperty("vote_count")
    @ColumnInfo(name = "vote_count")
    private Integer voteCount;

    @JsonProperty("id")
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @JsonProperty("video")
    private Boolean video;

    @JsonProperty("vote_average")
    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @JsonProperty("title")
    private String title;

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @JsonProperty("original_language")
    @ColumnInfo(name = "original_language")
    private String originalLanguage;

    @JsonProperty("original_title")
    @ColumnInfo(name = "original_title")
    private String originalTitle;

    @JsonProperty("genre_ids")
    @ColumnInfo(name = "genre_ids")
    private List<Integer> genreIds = null;

    @JsonProperty("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    @JsonProperty("adult")
    private Boolean adult;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("release_date")
    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @JsonIgnore
    private boolean isFavorite;

    public Movie() {

    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
