import React, { useState } from 'react';

function App() {
    const [mood, setMood] = useState('');
    const [platform, setPlatform] = useState('');
    const [movies, setMovies] = useState([]);

    const fetchMovies = async () => {
        const response = await fetch(`http://localhost:8080/api/movies/recommend?mood=${mood}&platform=${platform}`);
        const data = await response.json();
        setMovies(data);
    };

    return (
        <div>
            <h1>Mood-Based Movie Recommendation</h1>

            <label>
                Current Mood:
                <select value={mood} onChange={(e) => setMood(e.target.value)}>
                    <option value="Happy">Happy</option>
                    <option value="Sad">Sad</option>
                </select>
            </label>

            <label>
                Streaming Platform:
                <select value={platform} onChange={(e) => setPlatform(e.target.value)}>
                    <option value="Netflix">Netflix</option>
                    <option value="Prime">Prime</option>
                </select>
            </label>

            <button onClick={fetchMovies}>Get Recommendations</button>

            <div>
                {movies.map(movie => (
                    <div key={movie.id}>
                        <h3>{movie.title}</h3>
                        <a href={movie.movieUrl} target="_blank" rel="noopener noreferrer">Watch Now</a>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default App;
