import { useState, useEffect } from 'react';
import api from '../../api/axiosClient';
import { motion } from 'framer-motion';

interface WorkoutLog {
    id: number;
    exerciseName: string;
    duration: number;
    createdAt?: string;
}

const Workout = () => {
    const [logs, setLogs] = useState<WorkoutLog[]>([]);
    const [loading, setLoading] = useState(false);

    // Form state
    const [exerciseName, setExerciseName] = useState('');
    const [duration, setDuration] = useState('');
    const [message, setMessage] = useState('');

    const fetchLogs = async () => {
        try {
            const res = await api.get('/workout/history');
            if (res.data.status === 'success') {
                setLogs(res.data.data);
            }
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        fetchLogs();
    }, []);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        try {
            const res = await api.post('/workout/log', {
                exerciseName,
                duration: parseInt(duration)
            });

            if (res.data.status === 'success') {
                const newLog = res.data.data;
                setLogs(prev => [...prev, newLog]);
                setMessage('Workout logged successfully!');

                // Reset form
                setExerciseName('');
                setDuration('');
            }
        } catch (err) {
            console.error(err);
            setMessage('Failed to log workout.');
        } finally {
            setLoading(false);
            setTimeout(() => setMessage(''), 3000);
        }
    };

    return (
        <div style={{ padding: '24px' }}>
            <h2 className="gradient-text" style={{ fontSize: '2rem', marginBottom: '24px' }}>Workout Tracker</h2>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 2fr', gap: '32px' }}>
                {/* Form Section */}
                <motion.div
                    initial={{ opacity: 0, x: -20 }}
                    animate={{ opacity: 1, x: 0 }}
                    transition={{ duration: 0.5 }}
                    className="glass"
                    style={{ padding: '24px', borderRadius: '16px', height: 'fit-content' }}
                >
                    <h3 style={{ marginBottom: '20px', fontSize: '1.2rem' }}>Log Workout</h3>
                    {message && <p style={{ color: message.includes('Failed') ? '#ef4444' : '#10b981', marginBottom: '16px' }}>{message}</p>}

                    <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                        <div>
                            <label className="label">Activity Name</label>
                            <input
                                className="input"
                                type="text"
                                value={exerciseName}
                                onChange={(e) => setExerciseName(e.target.value)}
                                required
                                placeholder="Running, Lifting, Cycling..."
                            />
                        </div>

                        <div>
                            <label className="label">Duration (minutes)</label>
                            <input
                                className="input"
                                type="number"
                                min="1"
                                value={duration}
                                onChange={(e) => setDuration(e.target.value)}
                                required
                            />
                        </div>

                        <button
                            type="submit"
                            disabled={loading}
                            style={{
                                marginTop: '10px',
                                padding: '12px',
                                background: 'linear-gradient(135deg, var(--color-primary), var(--color-secondary))',
                                color: 'white',
                                borderRadius: '8px',
                                fontWeight: 600
                            }}
                        >
                            {loading ? 'Logging...' : 'Add Workout'}
                        </button>
                    </form>
                </motion.div>

                {/* History Section */}
                <motion.div
                    initial={{ opacity: 0, x: 20 }}
                    animate={{ opacity: 1, x: 0 }}
                    transition={{ duration: 0.5, delay: 0.2 }}
                    className="glass"
                    style={{ padding: '24px', borderRadius: '16px' }}
                >
                    <h3 style={{ marginBottom: '20px', fontSize: '1.2rem' }}>Today's Activities</h3>

                    {logs.length === 0 ? (
                        <p style={{ color: 'var(--text-secondary)' }}>No workouts logged today.</p>
                    ) : (
                        <div style={{ maxHeight: '400px', overflowY: 'auto' }}>
                            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                                <thead>
                                    <tr style={{ borderBottom: '1px solid var(--glass-border)', textAlign: 'left' }}>
                                        <th style={{ padding: '12px', color: 'var(--text-secondary)' }}>Time</th>
                                        <th style={{ padding: '12px', color: 'var(--text-secondary)' }}>Activity</th>
                                        <th style={{ padding: '12px', color: 'var(--text-secondary)' }}>Duration</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {logs.map((log, idx) => (
                                        <tr key={idx} style={{ borderBottom: '1px solid var(--glass-border)' }}>
                                            <td style={{ padding: '12px' }}>{log.createdAt ? new Date(log.createdAt.endsWith('Z') ? log.createdAt : log.createdAt + 'Z').toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) : '-'}</td>
                                            <td style={{ padding: '12px' }}>{log.exerciseName}</td>
                                            <td style={{ padding: '12px' }}>{log.duration} mins</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    )}
                </motion.div>
            </div>
        </div>
    );
};

export default Workout;
