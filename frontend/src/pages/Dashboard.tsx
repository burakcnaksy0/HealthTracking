import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Sidebar from '../components/dashboard/Sidebar';
import Overview from '../components/dashboard/Overview';
import Nutrition from '../components/dashboard/Nutrition';
import Workout from '../components/dashboard/Workout';
import Hydration from '../components/dashboard/Hydration';
import Reports from '../components/dashboard/Reports';

const Dashboard = () => {
    const navigate = useNavigate();
    const [activeTab, setActiveTab] = useState('overview');

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            navigate('/login');
        }
    }, [navigate]);

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        navigate('/login');
    };

    const renderContent = () => {
        switch (activeTab) {
            case 'overview':
                return <Overview setActiveTab={setActiveTab} />;
            case 'nutrition':
                return <Nutrition />;
            case 'workout':
                return <Workout />;
            case 'hydration':
                return <Hydration />;
            case 'reports':
                return <Reports />;
            default:
                return <Overview setActiveTab={setActiveTab} />;
        }
    };

    return (
        <div style={{ display: 'flex', minHeight: '100vh', background: 'var(--bg-primary)' }}>
            <Sidebar activeTab={activeTab} setActiveTab={setActiveTab} onLogout={handleLogout} />

            <main style={{
                flex: 1,
                marginLeft: '280px',
                padding: '40px',
                overflowY: 'auto'
            }}>
                {renderContent()}
            </main>
        </div>
    );
};

export default Dashboard;
