-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Gen 13, 2025 alle 21:50
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `triple_helix_cascina_caccia`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `bookings`
--

CREATE TABLE `bookings` (
  `booking_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `institute` varchar(70) NOT NULL,
  `participant_quantity` int(3) NOT NULL,
  `appointment_date` date NOT NULL,
  `time_slot` enum('MORNING','AFTERNOON','FULL_DAY') NOT NULL,
  `activity` varchar(50) NOT NULL,
  `status` enum('PENDING','CONFIRMED','REFUSED') NOT NULL,
  `booking_info_req` text DEFAULT NULL,
  `reminder_sent` tinyint(1) NOT NULL,
  `feedback_sent` tinyint(1) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `bookings`
--

INSERT INTO `bookings` (`booking_id`, `user_id`, `institute`, `participant_quantity`, `appointment_date`, `time_slot`, `activity`, `status`, `booking_info_req`, `reminder_sent`, `feedback_sent`, `created_at`, `updated_at`) VALUES
(5, 22, 'ITS ICT', 150, '2025-05-28', 'MORNING', 'Visita', 'CONFIRMED', NULL, 1, 1, '2025-01-13 17:19:25', '2025-01-13 17:19:25'),
(6, 22, 'ITS ICT', 100, '2025-01-02', 'MORNING', 'Visita', 'PENDING', NULL, 1, 1, '2025-01-13 17:19:25', '2025-01-13 17:19:25'),
(10, 22, 'ITS ICT', 97, '2025-01-06', 'AFTERNOON', 'Visita', 'CONFIRMED', 'Ciao, questa è una prova', 1, 1, '2025-01-13 21:07:21', '2025-01-13 21:46:32'),
(11, 22, 'ITS ICT', 97, '2025-01-03', 'AFTERNOON', 'Visita', 'PENDING', 'Ciao, questa è una prova', 0, 0, '2025-01-13 21:12:57', '2025-01-13 21:12:57');

-- --------------------------------------------------------

--
-- Struttura della tabella `feedbacks`
--

CREATE TABLE `feedbacks` (
  `feedback_id` int(11) NOT NULL,
  `which_lab` varchar(255) NOT NULL,
  `formative` int(11) NOT NULL,
  `engaging` int(11) NOT NULL,
  `staff_quality` int(11) NOT NULL,
  `recommend_lab` varchar(5) NOT NULL,
  `advices` text DEFAULT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `feedbacks`
--

INSERT INTO `feedbacks` (`feedback_id`, `which_lab`, `formative`, `engaging`, `staff_quality`, `recommend_lab`, `advices`, `date`) VALUES
(7, 'Bruno Caccia', 5, 3, 4, 'Si', 'Ottimo laboratorio', '2025-01-10 16:40:47'),
(9, 'Il gioco non è un azzardo', 4, 5, 4, 'Si', NULL, '2025-01-12 12:01:50'),
(10, 'Regolegalità per i più piccini', 5, 5, 5, 'Si', NULL, '2025-01-13 21:47:10');

-- --------------------------------------------------------

--
-- Struttura della tabella `roles`
--

CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `roles`
--

INSERT INTO `roles` (`role_id`, `role_name`) VALUES
(1, 'ADMIN'),
(2, 'USER');

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_surname` varchar(255) DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`user_id`, `user_name`, `user_surname`, `user_email`, `user_password`, `created_at`, `updated_at`, `role_id`) VALUES
(22, 'Lorenzo', 'Castiello', 'lorenzo.castiello04@gmail.com', '$2a$10$kIUhi2HfZVoIC5OuWwbDAeElr1AUhgRcDsa4mkFOOSi7A6rKWjyXG', '2025-01-08 16:26:12', '2025-01-13 21:21:16', 1),
(23, 'Mario', 'Rossi', 'mario.rossi@gmail.com', '$2a$10$2K/0cnTyOobLwKf.fu6A5eCPAr5./.SepdWYeET3./ajLRXDMcrMG', '2025-01-08 23:51:19', '2025-01-08 23:51:19', 2),
(29, 'Stefano', 'Cherio', 'stefano.cherio@edu.itspiemonte.it', '$2a$10$6iQdwT51RoreFm6nY9c9VOZLAkTbsElDFgFykg/SBsQLqLkk78KpW', '2025-01-09 21:38:00', '2025-01-09 21:38:00', 2),
(31, 'Alessio', 'Suppa', 'suppaalessio1@gmail.com', '$2a$10$CHoo.QeTOvVygg4VVeKDret1nXyALDVO1cSytiZr4lklGmshg6YYC', '2025-01-12 12:07:07', '2025-01-12 12:07:07', 2),
(32, 'Prova', 'Sito', 'provasito@example.com', '$2a$10$PBLWfsrNiWQGOXbBRpvdQOc4AuxrTj0HYL0SSvysPO7tjzuDX3JKq', '2025-01-13 21:19:57', '2025-01-13 21:19:57', 2);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`booking_id`),
  ADD KEY `fk_booking_user` (`user_id`);

--
-- Indici per le tabelle `feedbacks`
--
ALTER TABLE `feedbacks`
  ADD PRIMARY KEY (`feedback_id`);

--
-- Indici per le tabelle `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_email` (`user_email`),
  ADD KEY `fk_user_role` (`role_id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `bookings`
--
ALTER TABLE `bookings`
  MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT per la tabella `feedbacks`
--
ALTER TABLE `feedbacks`
  MODIFY `feedback_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT per la tabella `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `fk_booking_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Limiti per la tabella `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
