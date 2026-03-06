import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import logo from '../assets/logo.png';
import './Auth.css';

export default function Register() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [role, setRole] = useState('RENTER');
  const [error, setError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  const { register } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (password !== confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    setSubmitting(true);
    try {
      // Backend currently expects username/email/password.
      // Use email as username for now; extra fields can be wired later.
      await register(email, email, password, role);
      navigate('/login', { replace: true });
    } catch (err) {
      setError(err.message || 'Registration failed');
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
              Join our community of renters and owners and manage your stays with ease.
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
          <h1>Create Account</h1>
          <p className="auth-subtitle">Sign up for a new account</p>
          <form onSubmit={handleSubmit}>
            {error && <div className="auth-error">{error}</div>}

            <label>
              First Name
              <input
                type="text"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                required
                autoComplete="given-name"
              />
            </label>

            <label>
              Last Name
              <input
                type="text"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                required
                autoComplete="family-name"
              />
            </label>

            <label>
              Email
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
                autoComplete="new-password"
              />
            </label>

            <label>
              Confirm Password
              <input
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
                autoComplete="new-password"
              />
            </label>

            <label>
              Role
              <select value={role} onChange={(e) => setRole(e.target.value)}>
                <option value="RENTER">Renter</option>
                <option value="OWNER">Owner</option>
              </select>
            </label>

            <button type="submit" disabled={submitting} className="auth-button-full">
              {submitting ? 'Registering...' : 'Register'}
            </button>
          </form>
          <p className="auth-footer">
            Already have an account? <Link to="/login">Login</Link>
          </p>
        </div>
      </div>
    </div>
  );
}

