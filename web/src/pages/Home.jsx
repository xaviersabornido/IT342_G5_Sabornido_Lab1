import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './Home.css';

export default function Home() {
  const { isAuthenticated } = useAuth();

  return (
    <div className="home-page">
      <div className="home-card">
        <h1>Welcome</h1>
        <p className="home-card-lead">
          {isAuthenticated
            ? "You're signed in. Head to your dashboard to manage your account."
            : "Sign in to your account or create one to get started."}
        </p>
        <div className="home-actions">
          {isAuthenticated ? (
            <Link className="home-btn home-btn-primary" to="/dashboard">
              Go to Dashboard
            </Link>
          ) : (
            <>
              <Link className="home-btn home-btn-primary" to="/login">
                Log In
              </Link>
              <Link className="home-btn home-btn-secondary" to="/register">
                Register
              </Link>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
