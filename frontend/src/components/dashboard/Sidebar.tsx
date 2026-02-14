import { LayoutDashboard, Utensils, Activity, FileText, LogOut, Droplets } from 'lucide-react';

interface SidebarProps {
    activeTab: string;
    setActiveTab: (tab: string) => void;
    onLogout: () => void;
}

const Sidebar = ({ activeTab, setActiveTab, onLogout }: SidebarProps) => {
    const menuItems = [
        { id: 'overview', label: 'Overview', icon: LayoutDashboard },
        { id: 'nutrition', label: 'Nutrition', icon: Utensils },
        { id: 'workout', label: 'Workouts', icon: Activity },
        { id: 'hydration', label: 'Hydration', icon: Droplets },
        { id: 'reports', label: 'Reports & AI', icon: FileText },
    ];

    return (
        <div className="glass" style={{
            width: '280px',
            height: 'calc(100vh - 40px)',
            position: 'fixed',
            top: '20px',
            left: '20px',
            borderRadius: '24px',
            padding: '32px',
            display: 'flex',
            flexDirection: 'column',
            zIndex: 50
        }}>
            <div style={{ marginBottom: '40px', paddingLeft: '12px' }}>
                <h2 className="gradient-text" style={{ fontSize: '1.5rem' }}>HealthTrack</h2>
            </div>

            <div style={{ display: 'flex', flexDirection: 'column', gap: '8px', flex: 1 }}>
                {menuItems.map((item) => {
                    const Icon = item.icon;
                    const isActive = activeTab === item.id;
                    return (
                        <button
                            key={item.id}
                            onClick={() => setActiveTab(item.id)}
                            style={{
                                display: 'flex',
                                alignItems: 'center',
                                gap: '12px',
                                padding: '12px 16px',
                                borderRadius: '12px',
                                width: '100%',
                                background: isActive ? 'linear-gradient(135deg, var(--color-primary), var(--color-secondary))' : 'transparent',
                                color: isActive ? 'white' : 'var(--text-secondary)',
                                fontWeight: isActive ? 600 : 500,
                                textAlign: 'left',
                                transition: 'all 0.3s ease'
                            }}
                        >
                            <Icon size={20} />
                            {item.label}
                        </button>
                    );
                })}
            </div>

            <div style={{ borderTop: '1px solid var(--glass-border)', paddingTop: '20px' }}>
                <button
                    onClick={onLogout}
                    style={{
                        display: 'flex',
                        alignItems: 'center',
                        gap: '12px',
                        padding: '12px 16px',
                        borderRadius: '12px',
                        width: '100%',
                        color: '#ef4444',
                        fontWeight: 500,
                        textAlign: 'left'
                    }}
                >
                    <LogOut size={20} />
                    Logout
                </button>
            </div>
        </div>
    );
};

export default Sidebar;
