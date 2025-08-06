import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function App() {
    const [longUrl, setLongUrl] = useState('');
    const [result, setResult] = useState(null);
    const [error, setError] = useState('');
    const [copySuccess, setCopySuccess] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setResult(null);
        setError('');
        setCopySuccess('');

        if (!longUrl) {
            setError('Please enter a URL.');
            return;
        }

        try {
            // The backend API is running on localhost:8080
            const response = await axios.post('http://localhost:8080/api/v1/shorten', {
                longUrl: longUrl
            });
            setResult(response.data);
        } catch (err) {
            setError('Failed to shorten URL. Please ensure it is a valid URL and the server is running.');
            console.error(err);
        }
    };
    
    const handleCopy = () => {
        const shortUrl = `http://localhost:8080/${result.shortCode}`;
        navigator.clipboard.writeText(shortUrl).then(() => {
            setCopySuccess('Copied!');
            setTimeout(() => setCopySuccess(''), 2000); // Reset message after 2 seconds
        }, () => {
            setError('Failed to copy.');
        });
    };

    return (
        <div className="App">
            <h1>URL Shortener</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Enter a long URL to shorten"
                    value={longUrl}
                    onChange={(e) => setLongUrl(e.target.value)}
                />
                <button type="submit">Shorten</button>
            </form>

            {error && (
                <div className="result-container result-error">
                    <p>{error}</p>
                </div>
            )}

            {result && (
                <div className="result-container result-success">
                    <div className="short-url-display">
                        <span>{`http://localhost:8080/${result.shortCode}`}</span>
                        <button onClick={handleCopy}>
                            {copySuccess ? copySuccess : 'Copy'}
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default App;