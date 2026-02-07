import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './Dashboard.css';

export default function Dashboard() {
  const { user, logout } = useAuth();

  if (!user) return null;

  const createdAt = user.createdAt
    ? new Date(user.createdAt).toLocaleDateString()
    : 'N/A';

  return (
    <div className="dashboard-page">
      <header className="dashboard-header">
        <h1>Dashboard</h1>
        <div className="dashboard-actions">
          <span className="user-badge">{user.username}</span>
          <button onClick={logout} className="btn-logout">Log Out</button>
        </div>
      </header>
      <main className="dashboard-content">
        <div className="profile-card">
          <h2>Profile</h2>
          <dl>
            <dt>Username</dt>
            <dd>{user.username}</dd>
            <dt>Email</dt>
            <dd>{user.email}</dd>
            <dt>Member since</dt>
            <dd>{createdAt}</dd>
            <dt>Roles</dt>
            <dd>{user.roles?.join(', ') || 'USER'}</dd>
          </dl>
        </div>
      </main>
    </div>
  );
}
