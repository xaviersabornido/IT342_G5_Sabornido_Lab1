import { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import logo from '../assets/logo.png';
import './Auth.css';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  const { login, isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || '/dashboard';

  if (isAuthenticated) {
    navigate(from, { replace: true });
    return null;
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSubmitting(true);
    try {
      // For now we pass email as the identifier expected by the backend
      await login(email, password);
      navigate(from, { replace: true });
    } catch (err) {
      setError(err.message || 'Invalid email or password');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-card auth-card--split">
        <div className="auth-panel auth-panel-left">
          <div className="auth-logo-large">
            <img src={logo} alt="RentEase" />
          </div>
          <div className="auth-brand-copy">
            <h2 className="auth-brand-title">RentEase</h2>
            <p className="auth-brand-subtitle">
              Simplify your rental experience with our comprehensive property management platform.
            </p>
          </div>
          <div className="auth-features-row">
            <div className="auth-feature">
              <span className="auth-feature-icon">🔍</span>
              <span className="auth-feature-label">Find</span>
            </div>
            <div className="auth-feature">
              <span className="auth-feature-icon">🤝</span>
              <span className="auth-feature-label">Connect</span>
            </div>
            <div className="auth-feature">
              <span className="auth-feature-icon">🔑</span>
              <span className="auth-feature-label">Rent</span>
            </div>
          </div>
        </div>

        <div className="auth-panel auth-panel-right">
          <h1>Welcome Back</h1>
          <p className="auth-subtitle">Sign in to your account</p>
          <form onSubmit={handleSubmit}>
            {error && <div className="auth-error">{error}</div>}
            <label>
              Email Address
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                autoComplete="email"
              />
            </label>
            <label>
              Password
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                autoComplete="current-password"
              />
            </label>
            <button type="submit" disabled={submitting} className="auth-button-full">
              {submitting ? 'Signing in...' : 'Sign In'}
            </button>
          </form>
          <p className="auth-footer">
            Don&apos;t have an account?{' '}
            <Link to="/register">
              Sign up
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}
