-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 27, 2018 at 09:03 PM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `scmgame`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `id_barang` int(11) NOT NULL,
  `nama_barang` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`id_barang`, `nama_barang`) VALUES
(1, 'bbjagung'),
(2, 'bbgandum'),
(3, 'bbair'),
(4, 'bmsereal'),
(5, 'bmcoklat'),
(6, 'bmplastik'),
(7, 'bmkeju'),
(8, 'bjserealcoklat'),
(9, 'bjserealkeju'),
(10, 'bjturbo');

-- --------------------------------------------------------

--
-- Table structure for table `penjualan`
--

CREATE TABLE `penjualan` (
  `id_penjualan` int(11) NOT NULL,
  `id_barang` int(11) NOT NULL,
  `total_jual` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `score` int(11) NOT NULL,
  `scorepopularitas` int(11) NOT NULL,
  `uang` double NOT NULL,
  `bbjagung` int(11) NOT NULL,
  `bbgandum` int(11) NOT NULL,
  `bbsusu` int(11) NOT NULL,
  `bbgula` int(11) NOT NULL,
  `bmsereal` int(11) NOT NULL,
  `bmcoklat` int(11) NOT NULL,
  `bmplastik` int(11) NOT NULL,
  `bmkeju` int(11) NOT NULL,
  `bjserealcoklat` int(11) NOT NULL,
  `bjserealkeju` int(11) NOT NULL,
  `bjturbo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama`, `score`, `scorepopularitas`, `uang`, `bbjagung`, `bbgandum`, `bbsusu`, `bbgula`, `bmsereal`, `bmcoklat`, `bmplastik`, `bmkeju`, `bjserealcoklat`, `bjserealkeju`, `bjturbo`) VALUES
(1, 'abcd', 100, 100, 100000, 100, 1412, 1535, 0, 353253, 522, 325, 23, 145, 235, 124),
(2, 'nama', 0, 100, 1000, 10, 10, 100, 0, 2, 2, 5, 2, 0, 0, 0),
(3, 'asdas', 0, 100, 1000, 10, 10, 100, 0, 2, 2, 5, 2, 0, 0, 0),
(4, 'aaa', 0, 100, 1000, 10, 10, 100, 0, 2, 2, 5, 2, 0, 0, 0),
(5, 'aaa', 0, 100, 1000, 10, 10, 100, 0, 2, 2, 5, 2, 0, 0, 0),
(6, 'aaa', 0, 100, 1000, 10, 10, 100, 0, 2, 2, 5, 2, 0, 0, 0),
(7, 'aaaa', 0, 100, 1000, 10, 10, 100, 0, 2, 2, 5, 2, 0, 0, 0),
(8, 'aaa', 0, 100, 1000, 10, 10, 100, 0, 2, 2, 5, 2, 0, 0, 0),
(9, 'asc', 0, 100, 1000, 10, 10, 100, 0, 2, 2, 5, 2, 0, 0, 0),
(10, 'asdas', 0, 100, 1000, 10, 10, 100, 0, 2, 2, 5, 2, 0, 0, 0),
(11, 'asfasf', 0, 100, 1000, 10, 10, 100, 100, 2, 2, 5, 2, 0, 0, 0),
(12, 'asfagaeg', 0, 100, 1000, 10, 10, 100, 100, 2, 2, 5, 2, 0, 0, 0),
(13, 'asgeag', 0, 100, 1000, 10, 10, 100, 100, 2, 2, 5, 2, 0, 0, 0),
(14, 'aa', 0, 100, 1000, 10, 10, 100, 100, 2, 2, 5, 2, 0, 0, 0),
(15, 'v', 0, 100, 1000, 10, 10, 100, 100, 2, 2, 5, 2, 0, 0, 0),
(16, 'gg', 0, 100, 1000, 10, 10, 100, 100, 2, 2, 5, 2, 0, 0, 0),
(17, 'hg', 0, 100, 1000, 10, 10, 100, 100, 2, 2, 5, 2, 0, 0, 0),
(18, 'fg', 0, 100, 1000, 10, 10, 100, 100, 2, 2, 5, 2, 0, 0, 0),
(19, 'asfasf', 0, 100, 1000, 10, 10, 100, 100, 2, 2, 5, 2, 0, 0, 0),
(20, 'jnkjk', 0, 100, 1000, 10, 10, 100, 100, 2, 2, 5, 2, 0, 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id_barang`);

--
-- Indexes for table `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`id_penjualan`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `id_barang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `penjualan`
--
ALTER TABLE `penjualan`
  MODIFY `id_penjualan` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
