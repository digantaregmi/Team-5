package com.example.moodymovie.adaptor;

import com.example.moodymovie.utils.CommonUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class MovieDTODbAdaptorTest {

    @InjectMocks
    private MovieDbAdaptor movieDbAdaptor;

    @Mock
    private CommonUtils commonUtils;

    @Value("$(movie.db.url)")
    private String baseUrl;
    @Value("$(movie.db.url.api.version)")
    private String apiVersion;
    @Value("$(movie.db.bearer.token)")
    private String token;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMovieListByGenres() {
        List<String> genres = Arrays.asList("action", "comedy", "drama");

        when(commonUtils.loadGenres(genres)).thenReturn("28,35,18");

        movieDbAdaptor.getMovieListByGenres(genres);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl + "/" + apiVersion + "discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&with_genres=28,35,18")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", token)
                .build();

        verify(commonUtils, times(1)).loadGenres(genres);
    }
}
