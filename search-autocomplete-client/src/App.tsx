import React, { useState } from "react";
import axios from "axios";
import "./App.css";

type Suggestions = string[];

function App() {
  const [suggestions, setSuggestions] = useState<Suggestions>([]);
  const [query, setQuery] = useState<string>("");
  const [inputActive, setInputActive] = useState(false);
  const [showModal, setShowModal] = useState(false);

  function handleQueryChange(e: React.ChangeEvent<HTMLInputElement>) {
    setQuery(e.target.value);
    fetchQuerySuggestions(e.target.value);
  }

  async function fetchQuerySuggestions(prefix: string) {
    try {
      const { data } = await axios.get<Suggestions>(
        "http://localhost:8080/api/v1/suggest",
        {
          params: {
            queryPrefix: prefix,
          },
        },
      );

      setSuggestions(data);
      console.log(data);
    } catch (error) {
      console.error("Error fetching prefix suggestions: ", error);
    }
  }

  async function fetchQuery(query: string) {
    setInputActive(false);
    setShowModal(true);
    setSuggestions([]);
    setQuery("");
    try {
      await axios.get("http://localhost:8080/api/v1/search", {
        params: {
          query: query,
        },
      });
    } catch (error) {
      console.error("Error fetching query");
    }
  }

  function handleQuerySubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    fetchQuery(query);
  }

  function handleSuggestionClick(
    e: React.MouseEvent<HTMLLIElement, MouseEvent>,
  ) {
    e.stopPropagation();
    setInputActive(false);
    const queryContent = e.currentTarget.textContent;
    if (queryContent) {
      setQuery(queryContent);
      fetchQuery(queryContent);
    }
  }

  function setActive(
    e:
      | React.MouseEvent<HTMLInputElement, MouseEvent>
      | React.MouseEvent<HTMLUListElement>,
  ) {
    e.stopPropagation();
    setInputActive(true);
  }

  const suggestionsList = suggestions.map((suggestion) => (
    <li
      onClick={handleSuggestionClick}
      className="suggestions__item"
      key={suggestion}
    >
      {suggestion}
    </li>
  ));

  return (
    <main onClick={() => setInputActive(false)}>
      <section className="search container">
        <h1 className="search__title">Search Autocomplete</h1>
        <form
          onSubmit={handleQuerySubmit}
          className={
            inputActive && suggestions.length > 0
              ? "search__form active"
              : "search__form"
          }
        >
          <input
            onClick={setActive}
            className={
              inputActive && suggestions.length > 0
                ? "search__input active"
                : "search__input"
            }
            type="text"
            value={query}
            name="query"
            onChange={handleQueryChange}
            autoComplete="off"
          />
        </form>
        {inputActive && suggestions.length > 0 && (
          <ul onClick={setActive} className="search__suggestions suggestions">
            {suggestionsList}
          </ul>
        )}
      </section>
      {showModal && (
        <div className="modal">
          <div className="modal-content container">
            <h3>Query Submitterd</h3>
            <p>
              Your query has been recorded and will be aggregated to the list of
              available suggestions in the next update cycle.
            </p>
            <button onClick={() => setShowModal(false)}>Okay</button>
          </div>
        </div>
      )}
    </main>
  );
}
export default App;
