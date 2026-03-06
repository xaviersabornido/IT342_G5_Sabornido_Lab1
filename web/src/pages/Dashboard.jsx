import { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import logo from '../assets/logo.png';
import './Dashboard.css';

const ITEMS_PER_PAGE = 9; // 3 columns x 3 rows

export default function Dashboard() {
  const { user, logout } = useAuth();
  const [currentPage, setCurrentPage] = useState(1);

  if (!user) return null;

  const displayName = user.username || user.email || 'User';
  const listings = []; // Empty – no properties until owners add them
  const totalPages = Math.max(1, Math.ceil(listings.length / ITEMS_PER_PAGE));
  const startIdx = (currentPage - 1) * ITEMS_PER_PAGE;
  const paginatedListings = listings.slice(startIdx, startIdx + ITEMS_PER_PAGE);

  return (
    <div className="dashboard-page">
      <header className="dashboard-topbar">
        <div className="dashboard-brand">
          <img src={logo} alt="RentEase" className="dashboard-logo-img" />
          <span className="dashboard-logo-text">RentEase</span>
        </div>
        <div className="dashboard-topbar-actions">
          {(user.role === 'OWNER' || user.roles?.includes('OWNER')) && (
            <button type="button" className="btn-list-property">
              + List Property
            </button>
          )}
          <div className="dashboard-user-menu">
            <span className="dashboard-user-name">{displayName}</span>
            <button type="button" className="btn-logout" onClick={logout}>
              Log Out
            </button>
          </div>
        </div>
      </header>

      <main className="dashboard-main">
        <section className="dashboard-hero">
          <h1>Available Rentals</h1>
          <p>Discover your perfect rental property</p>
          <div className="dashboard-filters">
            <button type="button" className="filter-chip filter-chip--active">
              All Properties
            </button>
            <button type="button" className="filter-chip">
              Apartments
            </button>
            <button type="button" className="filter-chip">
              Condominiums
            </button>
            <button type="button" className="filter-chip">
              Dormitories
            </button>
          </div>
        </section>

        <section className="dashboard-listings">
          {paginatedListings.length === 0 ? (
            <p className="dashboard-empty">No properties available yet.</p>
          ) : (
            paginatedListings.map((listing) => (
              <article key={listing.id} className="listing-card">
                <div className="listing-image">
                  <span>{listing.imageLabel}</span>
                </div>
                <div className="listing-body">
                  <h2 className="listing-title">{listing.title}</h2>
                  <p className="listing-location">
                    <span className="listing-location-dot" />
                    {listing.location}
                  </p>
                  <p className="listing-price">
                    {listing.price}
                    <span className="listing-price-period">/month</span>
                  </p>
                  <p className="listing-description">{listing.description}</p>
                  <button type="button" className="listing-button">
                    View Details
                  </button>
                </div>
              </article>
            ))
          )}
        </section>

        {totalPages > 1 && (
          <nav className="dashboard-pagination">
            <button
              type="button"
              className="pagination-btn"
              disabled={currentPage === 1}
              onClick={() => setCurrentPage((p) => Math.max(1, p - 1))}
            >
              ‹
            </button>
            {Array.from({ length: totalPages }, (_, i) => i + 1).map((p) => (
              <button
                key={p}
                type="button"
                className={`pagination-btn ${p === currentPage ? 'pagination-btn--active' : ''}`}
                onClick={() => setCurrentPage(p)}
              >
                {p}
              </button>
            ))}
            <button
              type="button"
              className="pagination-btn"
              disabled={currentPage === totalPages}
              onClick={() => setCurrentPage((p) => Math.min(totalPages, p + 1))}
            >
              ›
            </button>
          </nav>
        )}
      </main>
    </div>
  );
}
