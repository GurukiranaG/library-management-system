import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';

export default function Dashboard() {
    const [activeTab, setActiveTab] = useState('books'); // 'books' or 'members'

    // Books state
    const [books, setBooks] = useState([]);
    const [loadingBooks, setLoadingBooks] = useState(true);
    const [isBookModalOpen, setIsBookModalOpen] = useState(false);
    const [bookForm, setBookForm] = useState({
        title: '',
        author: '',
        isbn: '',
        category: '',
        available: true
    });

    // Members state
    const [members, setMembers] = useState([]);
    const [loadingMembers, setLoadingMembers] = useState(true);
    const [isMemberModalOpen, setIsMemberModalOpen] = useState(false);
    const [memberForm, setMemberForm] = useState({
        name: '',
        email: '',
        phone: '',
        membershipNumber: ''
    });

    const [error, setError] = useState('');
    const username = localStorage.getItem('username') || 'admin';
    const navigate = useNavigate();

    useEffect(() => {
        fetchBooks();
        fetchMembers();
    }, []);

    const fetchBooks = async () => {
        try {
            setLoadingBooks(true);
            setError('');
            const response = await api.get('/api/books');
            setBooks(response.data);
        } catch (err) {
            console.error('Failed to load books:', err);
            setError('Failed to load book catalog.');
        } finally {
            setLoadingBooks(false);
        }
    };

    const fetchMembers = async () => {
        try {
            setLoadingMembers(true);
            const response = await api.get('/api/members');
            setMembers(response.data);
        } catch (err) {
            console.error('Failed to load members:', err);
            setError('Failed to load members list.');
        } finally {
            setLoadingMembers(false);
        }
    };

    // Book Handlers
    const handleBookChange = (e) => {
        const { name, value, type, checked } = e.target;
        setBookForm({ ...bookForm, [name]: type === 'checkbox' ? checked : value });
    };

    const handleAddBook = async (e) => {
        e.preventDefault();
        try {
            await api.post('/api/books', bookForm);
            setIsBookModalOpen(false);
            setBookForm({ title: '', author: '', isbn: '', category: '', available: true });
            fetchBooks();
        } catch (err) {
            console.error('Failed to add book:', err);
            alert('Failed to add book. Please check your inputs.');
        }
    };

    const handleDeleteBook = async (id) => {
        if (!window.confirm('Are you sure you want to delete this book?')) return;
        try {
            await api.delete(`/api/books/${id}`);
            fetchBooks();
        } catch (err) {
            console.error('Failed to delete book:', err);
            alert('Failed to delete book.');
        }
    };

    // Member Handlers
    const handleMemberChange = (e) => {
        const { name, value } = e.target;
        setMemberForm({ ...memberForm, [name]: value });
    };

    const handleAddMember = async (e) => {
        e.preventDefault();
        try {
            await api.post('/api/members', memberForm);
            setIsMemberModalOpen(false);
            setMemberForm({ name: '', email: '', phone: '', membershipNumber: '' });
            fetchMembers();
        } catch (err) {
            console.error('Failed to add member:', err);
            alert('Failed to add member. Please check inputs.');
        }
    };

    const handleDeleteMember = async (id) => {
        if (!window.confirm('Are you sure you want to delete this member?')) return;
        try {
            await api.delete(`/api/members/${id}`);
            fetchMembers();
        } catch (err) {
            console.error('Failed to delete member:', err);
            alert('Failed to delete member.');
        }
    };

    const handleLogout = () => {
        localStorage.clear();
        navigate('/login');
    };

    return (
        <div className="min-h-screen bg-slate-900 text-slate-100">
            {/* Navigation Bar */}
            <nav className="bg-slate-800 border-b border-slate-700 px-8 py-4 flex justify-between items-center shadow-md">
                <div className="flex items-center gap-3">
                    <svg className="w-6 h-6 text-indigo-400" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
                    </svg>
                    <span className="text-xl font-bold tracking-wide">Library Dashboard</span>
                </div>
                <div className="flex items-center gap-4">
                    <span className="text-sm text-slate-300">Welcome, <strong className="text-indigo-400">{username}</strong></span>
                    <button
                        onClick={handleLogout}
                        className="bg-slate-700 hover:bg-red-600/80 text-slate-200 hover:text-white px-4 py-2 rounded-lg text-sm font-medium transition duration-200 shadow-sm border border-slate-600"
                    >
                        Logout
                    </button>
                </div>
            </nav>

            {/* Main Content */}
            <main className="max-w-7xl mx-auto px-8 py-10">

                {/* Tab Header & Action Bar */}
                <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 mb-8">
                    <div className="flex bg-slate-800 p-1.5 rounded-xl border border-slate-700">
                        <button
                            onClick={() => setActiveTab('books')}
                            className={`px-5 py-2 rounded-lg text-sm font-medium transition ${activeTab === 'books' ? 'bg-indigo-600 text-white shadow' : 'text-slate-400 hover:text-white'}`}
                        >
                            📚 Book Catalog
                        </button>
                        <button
                            onClick={() => setActiveTab('members')}
                            className={`px-5 py-2 rounded-lg text-sm font-medium transition ${activeTab === 'members' ? 'bg-indigo-600 text-white shadow' : 'text-slate-400 hover:text-white'}`}
                        >
                            👥 Library Members
                        </button>
                    </div>

                    <div>
                        {activeTab === 'books' ? (
                            <button
                                onClick={() => setIsBookModalOpen(true)}
                                className="bg-indigo-600 hover:bg-indigo-500 text-white px-4 py-2.5 rounded-xl text-sm font-medium transition shadow-sm flex items-center gap-2"
                            >
                                + Add New Book
                            </button>
                        ) : (
                            <button
                                onClick={() => setIsMemberModalOpen(true)}
                                className="bg-indigo-600 hover:bg-indigo-500 text-white px-4 py-2.5 rounded-xl text-sm font-medium transition shadow-sm flex items-center gap-2"
                            >
                                + Register Member
                            </button>
                        )}
                    </div>
                </div>

                {error && (
                    <div className="mb-6 bg-red-500/10 border border-red-500/30 text-red-400 px-4 py-3 rounded-xl flex items-center gap-3 text-sm">
                        <span>{error}</span>
                    </div>
                )}

                {/* BOOKS VIEW */}
                {activeTab === 'books' && (
                    <div className="bg-slate-800 border border-slate-700 rounded-2xl overflow-hidden shadow-xl">
                        {loadingBooks ? (
                            <div className="p-12 text-center text-slate-400 animate-pulse">Loading book catalog...</div>
                        ) : books.length === 0 ? (
                            <div className="p-12 text-center text-slate-400">No books found in the system repository.</div>
                        ) : (
                            <div className="overflow-x-auto">
                                <table className="w-full text-left border-collapse">
                                    <thead>
                                    <tr className="bg-slate-900/50 border-b border-slate-700 text-xs uppercase tracking-wider text-slate-400">
                                        <th className="px-6 py-4">Title</th>
                                        <th className="px-6 py-4">Author</th>
                                        <th className="px-6 py-4">ISBN</th>
                                        <th className="px-6 py-4">Category</th>
                                        <th className="px-6 py-4">Status</th>
                                        <th className="px-6 py-4 text-right">Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody className="divide-y divide-slate-700/60 text-sm">
                                    {books.map((book) => (
                                        <tr key={book.id} className="hover:bg-slate-750 transition-colors">
                                            <td className="px-6 py-4 font-medium text-white">{book.title}</td>
                                            <td className="px-6 py-4 text-slate-300">{book.author}</td>
                                            <td className="px-6 py-4 text-slate-400 font-mono text-xs">{book.isbn}</td>
                                            <td className="px-6 py-4 text-slate-300">{book.category}</td>
                                            <td className="px-6 py-4">
                          <span className={`px-2.5 py-1 rounded-full text-xs font-semibold ${book.available ? 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/20' : 'bg-amber-500/10 text-amber-400 border border-amber-500/20'}`}>
                            {book.available ? 'Available' : 'Issued'}
                          </span>
                                            </td>
                                            <td className="px-6 py-4 text-right">
                                                <button
                                                    onClick={() => handleDeleteBook(book.id)}
                                                    className="text-red-400 hover:text-red-300 bg-red-500/10 hover:bg-red-500/20 border border-red-500/20 px-3 py-1.5 rounded-lg text-xs font-medium transition"
                                                >
                                                    Delete
                                                </button>
                                            </td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        )}
                    </div>
                )}

                {/* MEMBERS VIEW */}
                {activeTab === 'members' && (
                    <div className="bg-slate-800 border border-slate-700 rounded-2xl overflow-hidden shadow-xl">
                        {loadingMembers ? (
                            <div className="p-12 text-center text-slate-400 animate-pulse">Loading members directory...</div>
                        ) : members.length === 0 ? (
                            <div className="p-12 text-center text-slate-400">No members registered in the system yet.</div>
                        ) : (
                            <div className="overflow-x-auto">
                                <table className="w-full text-left border-collapse">
                                    <thead>
                                    <tr className="bg-slate-900/50 border-b border-slate-700 text-xs uppercase tracking-wider text-slate-400">
                                        <th className="px-6 py-4">Name</th>
                                        <th className="px-6 py-4">Email</th>
                                        <th className="px-6 py-4">Phone</th>
                                        <th className="px-6 py-4">Membership No.</th>
                                        <th className="px-6 py-4 text-right">Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody className="divide-y divide-slate-700/60 text-sm">
                                    {members.map((member) => (
                                        <tr key={member.id} className="hover:bg-slate-750 transition-colors">
                                            <td className="px-6 py-4 font-medium text-white">{member.name}</td>
                                            <td className="px-6 py-4 text-slate-300">{member.email}</td>
                                            <td className="px-6 py-4 text-slate-400">{member.phone}</td>
                                            <td className="px-6 py-4 font-mono text-xs text-indigo-400">{member.membershipNumber}</td>
                                            <td className="px-6 py-4 text-right">
                                                <button
                                                    onClick={() => handleDeleteMember(member.id)}
                                                    className="text-red-400 hover:text-red-300 bg-red-500/10 hover:bg-red-500/20 border border-red-500/20 px-3 py-1.5 rounded-lg text-xs font-medium transition"
                                                >
                                                    Delete
                                                </button>
                                            </td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        )}
                    </div>
                )}
            </main>

            {/* Add Book Modal */}
            {isBookModalOpen && (
                <div className="fixed inset-0 bg-black/70 backdrop-blur-sm flex justify-center items-center z-50 p-4">
                    <div className="bg-slate-800 border border-slate-700 rounded-2xl max-w-md w-full p-6 shadow-2xl">
                        <div className="flex justify-between items-center mb-4">
                            <h2 className="text-xl font-bold text-white">Add New Book</h2>
                            <button onClick={() => setIsBookModalOpen(false)} className="text-slate-400 hover:text-white text-lg font-bold">&times;</button>
                        </div>
                        <form onSubmit={handleAddBook} className="space-y-4">
                            <div>
                                <label className="block text-xs font-semibold uppercase tracking-wider text-slate-400 mb-1">Title</label>
                                <input type="text" name="title" value={bookForm.title} onChange={handleBookChange} required className="w-full bg-slate-900 border border-slate-700 rounded-xl px-4 py-2.5 text-white focus:outline-none focus:border-indigo-500" />
                            </div>
                            <div>
                                <label className="block text-xs font-semibold uppercase tracking-wider text-slate-400 mb-1">Author</label>
                                <input type="text" name="author" value={bookForm.author} onChange={handleBookChange} required className="w-full bg-slate-900 border border-slate-700 rounded-xl px-4 py-2.5 text-white focus:outline-none focus:border-indigo-500" />
                            </div>
                            <div>
                                <label className="block text-xs font-semibold uppercase tracking-wider text-slate-400 mb-1">ISBN</label>
                                <input type="text" name="isbn" value={bookForm.isbn} onChange={handleBookChange} required className="w-full bg-slate-900 border border-slate-700 rounded-xl px-4 py-2.5 text-white focus:outline-none focus:border-indigo-500" />
                            </div>
                            <div>
                                <label className="block text-xs font-semibold uppercase tracking-wider text-slate-400 mb-1">Category</label>
                                <input type="text" name="category" value={bookForm.category} onChange={handleBookChange} required className="w-full bg-slate-900 border border-slate-700 rounded-xl px-4 py-2.5 text-white focus:outline-none focus:border-indigo-500" />
                            </div>
                            <div className="flex items-center gap-2 pt-2">
                                <input type="checkbox" name="available" id="available" checked={bookForm.available} onChange={handleBookChange} className="w-4 h-4 rounded bg-slate-900 border-slate-700 text-indigo-600 focus:ring-indigo-500" />
                                <label htmlFor="available" className="text-sm text-slate-300 font-medium">Available in library</label>
                            </div>
                            <div className="flex justify-end gap-3 pt-4 border-t border-slate-700">
                                <button type="button" onClick={() => setIsBookModalOpen(false)} className="px-4 py-2 rounded-xl text-sm font-medium bg-slate-700 text-slate-300 hover:bg-slate-600 transition">Cancel</button>
                                <button type="submit" className="px-5 py-2 rounded-xl text-sm font-medium bg-indigo-600 text-white hover:bg-indigo-500 transition shadow-lg shadow-indigo-600/20">Save Book</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {/* Register Member Modal */}
            {isMemberModalOpen && (
                <div className="fixed inset-0 bg-black/70 backdrop-blur-sm flex justify-center items-center z-50 p-4">
                    <div className="bg-slate-800 border border-slate-700 rounded-2xl max-w-md w-full p-6 shadow-2xl">
                        <div className="flex justify-between items-center mb-4">
                            <h2 className="text-xl font-bold text-white">Register New Member</h2>
                            <button onClick={() => setIsMemberModalOpen(false)} className="text-slate-400 hover:text-white text-lg font-bold">&times;</button>
                        </div>
                        <form onSubmit={handleAddMember} className="space-y-4">
                            <div>
                                <label className="block text-xs font-semibold uppercase tracking-wider text-slate-400 mb-1">Full Name</label>
                                <input type="text" name="name" value={memberForm.name} onChange={handleMemberChange} required className="w-full bg-slate-900 border border-slate-700 rounded-xl px-4 py-2.5 text-white focus:outline-none focus:border-indigo-500" />
                            </div>
                            <div>
                                <label className="block text-xs font-semibold uppercase tracking-wider text-slate-400 mb-1">Email</label>
                                <input type="email" name="email" value={memberForm.email} onChange={handleMemberChange} required className="w-full bg-slate-900 border border-slate-700 rounded-xl px-4 py-2.5 text-white focus:outline-none focus:border-indigo-500" />
                            </div>
                            <div>
                                <label className="block text-xs font-semibold uppercase tracking-wider text-slate-400 mb-1">Phone</label>
                                <input type="text" name="phone" value={memberForm.phone} onChange={handleMemberChange} required className="w-full bg-slate-900 border border-slate-700 rounded-xl px-4 py-2.5 text-white focus:outline-none focus:border-indigo-500" />
                            </div>
                            <div>
                                <label className="block text-xs font-semibold uppercase tracking-wider text-slate-400 mb-1">Membership Number</label>
                                <input type="text" name="membershipNumber" value={memberForm.membershipNumber} onChange={handleMemberChange} required className="w-full bg-slate-900 border border-slate-700 rounded-xl px-4 py-2.5 text-white focus:outline-none focus:border-indigo-500" />
                            </div>
                            <div className="flex justify-end gap-3 pt-4 border-t border-slate-700">
                                <button type="button" onClick={() => setIsMemberModalOpen(false)} className="px-4 py-2 rounded-xl text-sm font-medium bg-slate-700 text-slate-300 hover:bg-slate-600 transition">Cancel</button>
                                <button type="submit" className="px-5 py-2 rounded-xl text-sm font-medium bg-indigo-600 text-white hover:bg-indigo-500 transition shadow-lg shadow-indigo-600/20">Register Member</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}