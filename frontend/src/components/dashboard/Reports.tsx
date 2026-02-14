import { useState, useEffect } from 'react';
import api from '../../api/axiosClient';
import { motion } from 'framer-motion';

const Reports = () => {
    const [dailyRec, setDailyRec] = useState<any>(null);
    const [weeklyRep, setWeeklyRep] = useState<any>(null);
    const [activeTab, setActiveTab] = useState<'daily' | 'weekly'>('daily');
    const [loading, setLoading] = useState(false);

    const fetchDaily = async () => {
        setLoading(true);
        try {
            const res = await api.get('/recommendations/daily');
            setDailyRec(res.data.data);
        } catch (err) {
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const fetchWeekly = async () => {
        setLoading(true);
        try {
            const res = await api.get('/reports/weekly');
            setWeeklyRep(res.data.data);
        } catch (err) {
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (activeTab === 'daily' && !dailyRec) fetchDaily();
        if (activeTab === 'weekly' && !weeklyRep) fetchWeekly();
    }, [activeTab]);

    return (
        <div style={{ padding: '24px' }}>
            <h2 className="gradient-text" style={{ fontSize: '2rem', marginBottom: '32px' }}>Smart Insights</h2>

            <div style={{ display: 'flex', gap: '24px', marginBottom: '32px' }}>
                <button
                    onClick={() => setActiveTab('daily')}
                    style={{
                        padding: '12px 24px',
                        borderRadius: '12px',
                        background: activeTab === 'daily' ? 'var(--color-primary)' : 'rgba(255, 255, 255, 0.05)',
                        color: 'white',
                        fontWeight: 600
                    }}
                >
                    Daily AI Advice
                </button>
                <button
                    onClick={() => setActiveTab('weekly')}
                    style={{
                        padding: '12px 24px',
                        borderRadius: '12px',
                        background: activeTab === 'weekly' ? 'var(--color-primary)' : 'rgba(255, 255, 255, 0.05)',
                        color: 'white',
                        fontWeight: 600
                    }}
                >
                    Weekly Report
                </button>
            </div>

            <motion.div
                key={activeTab}
                initial={{ opacity: 0, y: 10 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.3 }}
                className="glass"
                style={{ padding: '32px', borderRadius: '24px', minHeight: '300px' }}
            >
                {loading ? (
                    <p>Loading insights...</p>
                ) : (
                    <>
                        {activeTab === 'daily' && (
                            <div>
                                <h3 style={{ fontSize: '1.5rem', marginBottom: '20px' }}>Today's Recommendation</h3>
                                {dailyRec ? (
                                    <div style={{ lineHeight: '1.6', fontSize: '1.1rem', whiteSpace: 'pre-wrap' }}>
                                        {dailyRec.recommendationText}
                                        <div style={{ marginTop: '20px', fontSize: '0.9rem', color: 'var(--text-secondary)' }}>
                                            Generated at: {new Date(dailyRec.generatedAt).toLocaleString()}
                                        </div>
                                    </div>
                                ) : (
                                    <div>
                                        <p>No recommendation generated yet. Try adding some logs first!</p>
                                        <button onClick={fetchDaily} style={{ marginTop: '16px', color: 'var(--color-accent)' }}>Refresh</button>
                                    </div>
                                )}
                            </div>
                        )}

                        {activeTab === 'weekly' && (
                            <div>
                                <h3 style={{ fontSize: '1.5rem', marginBottom: '20px' }}>Weekly Summary</h3>
                                {weeklyRep ? (
                                    <div style={{ display: 'flex', flexDirection: 'column', gap: '30px' }}>
                                        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '20px' }}>
                                            <div style={{ padding: '20px', background: 'rgba(255,255,255,0.05)', borderRadius: '16px' }}>
                                                <h4 style={{ color: 'var(--text-secondary)', marginBottom: '8px' }}>Calories In</h4>
                                                <p style={{ fontSize: '1.8rem', fontWeight: 700, color: 'var(--color-primary)' }}>
                                                    {weeklyRep.totalCaloriesIn || 0} kcal
                                                </p>
                                            </div>
                                            <div style={{ padding: '20px', background: 'rgba(255,255,255,0.05)', borderRadius: '16px' }}>
                                                <h4 style={{ color: 'var(--text-secondary)', marginBottom: '8px' }}>Calories Out</h4>
                                                <p style={{ fontSize: '1.8rem', fontWeight: 700, color: 'var(--color-secondary)' }}>
                                                    {weeklyRep.totalCaloriesOut || 0} kcal
                                                </p>
                                            </div>
                                            <div style={{ padding: '20px', background: 'rgba(255,255,255,0.05)', borderRadius: '16px' }}>
                                                <h4 style={{ color: 'var(--text-secondary)', marginBottom: '8px' }}>Avg Water</h4>
                                                <p style={{ fontSize: '1.8rem', fontWeight: 700, color: 'var(--color-accent)' }}>
                                                    {weeklyRep.averageWater || 0} ml
                                                </p>
                                            </div>
                                        </div>

                                        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '16px' }}>
                                            <div style={{ textAlign: 'center', padding: '16px', background: 'rgba(255,255,255,0.02)', borderRadius: '12px' }}>
                                                <span style={{ display: 'block', color: 'var(--text-secondary)' }}>Protein</span>
                                                <span style={{ fontSize: '1.2rem', fontWeight: 600 }}>{weeklyRep.averageProtein || 0}g</span>
                                            </div>
                                            <div style={{ textAlign: 'center', padding: '16px', background: 'rgba(255,255,255,0.02)', borderRadius: '12px' }}>
                                                <span style={{ display: 'block', color: 'var(--text-secondary)' }}>Carbs</span>
                                                <span style={{ fontSize: '1.2rem', fontWeight: 600 }}>{weeklyRep.averageCarb || 0}g</span>
                                            </div>
                                            <div style={{ textAlign: 'center', padding: '16px', background: 'rgba(255,255,255,0.02)', borderRadius: '12px' }}>
                                                <span style={{ display: 'block', color: 'var(--text-secondary)' }}>Fat</span>
                                                <span style={{ fontSize: '1.2rem', fontWeight: 600 }}>{weeklyRep.averageFat || 0}g</span>
                                            </div>
                                        </div>

                                        <div className="glass" style={{ padding: '24px', borderRadius: '16px', lineHeight: '1.6' }}>
                                            <h4 style={{ marginBottom: '12px', color: 'var(--color-accent)' }}>AI Analysis</h4>
                                            <p style={{ whiteSpace: 'pre-wrap' }}>{weeklyRep.reportText}</p>
                                        </div>
                                    </div>
                                ) : (
                                    <p>No weekly report available.</p>
                                )}
                            </div>
                        )}
                    </>
                )}
            </motion.div>
        </div>
    );
};

export default Reports;
