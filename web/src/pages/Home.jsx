import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './Home.css';

export default function Home() {
  const { isAuthenticated } = useAuth();

  return (
    <div className="home-page">
      <h1>Welcome to Our Platform</h1>
      <p>
        {isAuthenticated
          ? "Access your dashboard to manage your account and explore features."
          : "Log in or register to get started and experience all the benefits."}
      </p>

      <div className="home-buttons">
        {isAuthenticated ? (
          <Link className="cta-button" to="/dashboard">
            Go to Dashboard
          </Link>
        ) : (
          <>
            <Link className="cta-button" to="/login">
              Log In
            </Link>
            <span className="button-separator">or</span>
            <Link className="cta-button" to="/register">
              Register
            </Link>
          </>
        )}
      </div>
    </div>
  );
}
