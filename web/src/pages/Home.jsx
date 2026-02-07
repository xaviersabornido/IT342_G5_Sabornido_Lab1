import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './Home.css';

export default function Home() {
  const { isAuthenticated } = useAuth();

  return (
    <div className="home-page">
      <h1>Welcome</h1>
      <p>
        {isAuthenticated ? (
          <>
            <Link to="/dashboard">Go to Dashboard</Link>
          </>
        ) : (
          <>
            <Link to="/login">Log In</Link> or <Link to="/register">Register</Link> to get started.
          </>
        )}
      </p>
    </div>
  );
}
