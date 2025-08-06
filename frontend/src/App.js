import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

// A simple SVG icon component for the header
const LinkIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" className="icon" viewBox="0 0 20 20" fill="currentColor">
    <path fillRule="evenodd" d="M12.586 4.586a2 2 0 112.828 2.828l-3 3a2 2 0 01-2.828 0m-4.879 4.879a2 2 0 010-2.828l3-3a2 2 0 112.828 2.828l-3 3a2 2 0 01-2.828 0z" clipRule="evenodd" />
    <path fillRule="evenodd" d="M9 11a1 1 0 10-2 0v1H6a1 1 0 100 2h1v1a1 1 0 102 0v-1h1a1 1 0 100-2h-1v-1z" clipRule="evenodd" />
  </svg>
);


function App() {
  const [urlsInput, setUrlsInput] = useState('');
  const [results, setResults] = useState([]);
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false); // New state for loading feedback

  const handleSubmit = async (e) => {
    e.preventDefault();
    setResults([]);
    setError('');
    setIsLoading(true); // Start loading

    const urls = urlsInput.split('\n').map(url => url.trim()).filter(Boolean);

    if (urls.length === 0) {
      setError('Please enter at least one URL.');
      setIsLoading(false); // Stop loading
      return;
    }

    try {
      const apiUrl = process.env.REACT_APP_API_URL;
      const response = await axios.post(`${apiUrl}/api/v1/shorten-batch`, {
          urls: urls
      });
      setResults(response.data);
    } catch (err) {
      setError('An error occurred. Please check the server logs and ensure it is running.');
      console.error(err);
    } finally {
      setIsLoading(false); // Stop loading, regardless of success or failure
    }
  };

  return (
    <div className="container">
      <header className="app-header">
        <LinkIcon />
        <h1>QuickLink</h1>
      </header>
      
      <main className="app-main">
        <div className="hero-section">
          <h2>Shorten. Share. Analyze.</h2>
          <p>Create clean, memorable links and track their performance. Paste your long URLs below to get started.</p>
        </div>

        <form className="shortener-form" onSubmit={handleSubmit}>
          <textarea
            placeholder="Paste your long URLs here, one per line..."
            value={urlsInput}
            onChange={(e) => setUrlsInput(e.target.value)}
            rows="8"
            disabled={isLoading}
          />
          <button type="submit" disabled={isLoading}>
            {isLoading ? 'Shortening...' : 'Shorten All'}
          </button>
        </form>

        {error && <div className="error-box">{error}</div>}

        {results.length > 0 && (
          <section className="results-section">
            <h3>Results</h3>
            <div className="results-grid">
              {results.map((result, index) => (
                <ResultCard key={index} result={result} />
              ))}
            </div>
          </section>
        )}
      </main>

      <footer className="app-footer">
        <p>Created by Prashanth Reddy</p>
        <a href="https://github.com/Prashanthpcr/url-shortener-platform" target="_blank" rel="noopener noreferrer">
          View on GitHub
        </a>
      </footer>
    </div>
  );
}

// A new component for the "Copy" icon
const CopyIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" className="icon-copy" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
    <path strokeLinecap="round" strokeLinejoin="round" d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
  </svg>
);

const ResultCard = ({ result }) => {
  const [copySuccess, setCopySuccess] = useState('');

  const handleCopy = () => {
    if (!result.success) return;
    navigator.clipboard.writeText(result.shortUrl).then(() => {
      setCopySuccess('Copied!');
      setTimeout(() => setCopySuccess(''), 2000);
    });
  };

  const cardClassName = `result-card ${result.success ? 'success' : 'error'}`;

  return (
    <div className={cardClassName}>
      <p className="original-url" title={result.longUrl}>{result.longUrl}</p>
      <div className="result-content">
        {result.success ? (
          <>
            <a href={result.shortUrl} target="_blank" rel="noopener noreferrer" className="short-url">
              {result.shortUrl.replace(/^https?:\/\//, '')}
            </a>
            <button className="copy-button" onClick={handleCopy} title="Copy to clipboard">
              {copySuccess ? '✓' : <CopyIcon />}
            </button>
          </>
        ) : (
          <span className="error-message">Error: {result.error}</span>
        )}
      </div>
    </div>
  );
};

export default App;