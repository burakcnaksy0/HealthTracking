import { motion } from 'framer-motion';

const Overview = ({ setActiveTab }: { setActiveTab: (tab: string) => void }) => {
    return (
        <div style={{ padding: '24px' }}>
            <h2 className="gradient-text" style={{ fontSize: '2rem', marginBottom: '32px' }}>Welcome Back</h2>

            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '24px' }}>
                <motion.div
                    whileHover={{ scale: 1.02 }}
                    className="glass"
                    style={{ padding: '32px', borderRadius: '24px', cursor: 'pointer', background: 'linear-gradient(135deg, rgba(139, 92, 246, 0.1), rgba(0,0,0,0))' }}
                    onClick={() => setActiveTab('nutrition')}
                >
                    <h3 style={{ fontSize: '1.5rem', marginBottom: '12px', color: 'var(--color-primary)' }}>Nutrition</h3>
                    <p style={{ color: 'var(--text-secondary)' }}>Log your meals and track calories.</p>
                </motion.div>

                <motion.div
                    whileHover={{ scale: 1.02 }}
                    className="glass"
                    style={{ padding: '32px', borderRadius: '24px', cursor: 'pointer', background: 'linear-gradient(135deg, rgba(236, 72, 153, 0.1), rgba(0,0,0,0))' }}
                    onClick={() => setActiveTab('workout')}
                >
                    <h3 style={{ fontSize: '1.5rem', marginBottom: '12px', color: 'var(--color-secondary)' }}>Workouts</h3>
                    <p style={{ color: 'var(--text-secondary)' }}>Track your exercises and progress.</p>
                </motion.div>

                <motion.div
                    whileHover={{ scale: 1.02 }}
                    className="glass"
                    style={{ padding: '32px', borderRadius: '24px', cursor: 'pointer', background: 'linear-gradient(135deg, rgba(6, 182, 212, 0.1), rgba(0,0,0,0))' }}
                    onClick={() => setActiveTab('hydration')}
                >
                    <h3 style={{ fontSize: '1.5rem', marginBottom: '12px', color: 'var(--color-accent)' }}>Hydration</h3>
                    <p style={{ color: 'var(--text-secondary)' }}>Keep track of your water intake.</p>
                </motion.div>

                <motion.div
                    whileHover={{ scale: 1.02 }}
                    className="glass"
                    style={{ padding: '32px', borderRadius: '24px', cursor: 'pointer', background: 'linear-gradient(135deg, rgba(245, 158, 11, 0.1), rgba(0,0,0,0))' }}
                    onClick={() => setActiveTab('reports')}
                >
                    <h3 style={{ fontSize: '1.5rem', marginBottom: '12px', color: '#f59e0b' }}>AI Insights</h3>
                    <p style={{ color: 'var(--text-secondary)' }}>View daily recommendations and reports.</p>
                </motion.div>
            </div>
        </div>
    );
};

export default Overview;
