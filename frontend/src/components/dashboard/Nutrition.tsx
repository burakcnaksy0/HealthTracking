import { useState, useEffect } from 'react';
import api from '../../api/axiosClient';
import { motion } from 'framer-motion';

interface NutritionLog {
    id: number;
    mealTime: string;
    foodName: string;
    amount: number;
    unit: string;
    createdAt?: string;
}

const Nutrition = () => {
    const [logs, setLogs] = useState<NutritionLog[]>([]);
    const [loading, setLoading] = useState(false);

    // Form state
    const [mealTime, setMealTime] = useState('BREAKFAST');
    const [foodName, setFoodName] = useState('');
    const [amount, setAmount] = useState('');
    const [unit, setUnit] = useState('grams');
    const [message, setMessage] = useState('');

    const fetchLogs = async () => {
        try {
            const res = await api.get('/nutrition/history');
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
            const res = await api.post('/nutrition/log', {
                mealTime,
                foodName,
                amount: parseFloat(amount),
                unit
            });

            if (res.data.status === 'success') {
                // Add the newly created log directly to the list
                const newLog = res.data.data;
                setLogs(prev => [...prev, newLog]);
                setMessage('Meal logged successfully!');

                // Reset form
                setFoodName('');
                setAmount('');
            }
        } catch (err) {
            console.error(err);
            setMessage('Failed to log meal.');
        } finally {
            setLoading(false);
            setTimeout(() => setMessage(''), 3000);
        }
    };

    return (
        <div style={{ padding: '24px' }}>
            <h2 className="gradient-text" style={{ fontSize: '2rem', marginBottom: '24px' }}>Nutrition Tracking</h2>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 2fr', gap: '32px' }}>
                {/* Form Section */}
                <motion.div
                    initial={{ opacity: 0, x: -20 }}
                    animate={{ opacity: 1, x: 0 }}
                    transition={{ duration: 0.5 }}
                    className="glass"
                    style={{ padding: '24px', borderRadius: '16px', height: 'fit-content' }}
                >
                    <h3 style={{ marginBottom: '20px', fontSize: '1.2rem' }}>Log a Meal</h3>
                    {message && <p style={{ color: message.includes('Failed') ? '#ef4444' : '#10b981', marginBottom: '16px' }}>{message}</p>}

                    <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                        <div>
                            <label className="label">Meal Time</label>
                            <select className="input" value={mealTime} onChange={(e) => setMealTime(e.target.value)}>
                                <option value="BREAKFAST" style={{ color: 'black' }}>Breakfast</option>
                                <option value="LUNCH" style={{ color: 'black' }}>Lunch</option>
                                <option value="DINNER" style={{ color: 'black' }}>Dinner</option>
                                <option value="SNACK" style={{ color: 'black' }}>Snack</option>
                            </select>
                        </div>

                        <div>
                            <label className="label">Food Name (e.g. Apply, Chicken)</label>
                            <input
                                className="input"
                                type="text"
                                value={foodName}
                                onChange={(e) => setFoodName(e.target.value)}
                                required
                                placeholder="Search food..."
                            />
                        </div>

                        <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr', gap: '12px' }}>
                            <div>
                                <label className="label">Amount</label>
                                <input
                                    className="input"
                                    type="number"
                                    step="0.1"
                                    min="0.1"
                                    value={amount}
                                    onChange={(e) => setAmount(e.target.value)}
                                    required
                                    placeholder="Qty"
                                />
                            </div>
                            <div>
                                <label className="label">Unit</label>
                                <select className="input" value={unit} onChange={(e) => setUnit(e.target.value)}>
                                    <option value="grams" style={{ color: 'black' }}>grams</option>
                                    <option value="ml" style={{ color: 'black' }}>ml</option>
                                    <option value="slice" style={{ color: 'black' }}>slice</option>
                                    <option value="cup" style={{ color: 'black' }}>cup</option>
                                    <option value="pcs" style={{ color: 'black' }}>pcs</option>
                                </select>
                            </div>
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
                            {loading ? 'Logging...' : 'Add Log'}
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
                    <h3 style={{ marginBottom: '20px', fontSize: '1.2rem' }}>Today's History</h3>

                    {logs.length === 0 ? (
                        <p style={{ color: 'var(--text-secondary)' }}>No meals logged today yet.</p>
                    ) : (
                        <div style={{ maxHeight: '400px', overflowY: 'auto' }}>
                            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                                <thead>
                                    <tr style={{ borderBottom: '1px solid var(--glass-border)', textAlign: 'left' }}>
                                        <th style={{ padding: '12px', color: 'var(--text-secondary)' }}>Time</th>
                                        <th style={{ padding: '12px', color: 'var(--text-secondary)' }}>Food</th>
                                        <th style={{ padding: '12px', color: 'var(--text-secondary)' }}>Qty</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {logs.map((log, idx) => (
                                        <tr key={idx} style={{ borderBottom: '1px solid var(--glass-border)' }}>
                                            <td style={{ padding: '12px' }}>
                                                <div style={{ fontWeight: 600 }}>{log.createdAt ? new Date(log.createdAt.endsWith('Z') ? log.createdAt : log.createdAt + 'Z').toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) : '-'}</div>
                                                <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>{log.mealTime}</div>
                                            </td>
                                            <td style={{ padding: '12px' }}>{log.foodName}</td>
                                            <td style={{ padding: '12px' }}>{log.amount} {log.unit}</td>
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

export default Nutrition;
