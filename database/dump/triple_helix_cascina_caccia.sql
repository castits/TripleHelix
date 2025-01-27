-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Gen 27, 2025 alle 11:59
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
(18, 22, 'ITS ICT', 7, '2025-01-28', 'AFTERNOON', 'Visita', 'PENDING', 'Ciao, questa è una prova', 1, 1, '2025-01-24 12:35:58', '2025-01-25 13:54:14'),
(20, 22, 'ITS ICT', 121, '2025-01-31', 'MORNING', 'Regolegalità per i più piccini', 'PENDING', 'dadsasdadsasdadsadsasd', 0, 0, '2025-01-25 15:05:02', '2025-01-25 15:05:02'),
(21, 22, 'ITS ICT', 7, '2025-01-17', 'AFTERNOON', 'Visita', 'PENDING', 'Ciao, questa è una prova', 0, 0, '2025-01-25 15:07:36', '2025-01-25 15:07:36'),
(26, 22, 'ITS ICT', 23, '2025-01-30', 'AFTERNOON', 'Bruno Caccia', 'PENDING', 'asdasdasedasdasd', 1, 0, '2025-01-27 10:44:24', '2025-01-27 10:44:26');

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
(17, 'Miele e api', 5, 5, 4, 'Si', NULL, '2025-01-24 12:36:44'),
(19, 'Bruno Caccia', 5, 5, 5, 'Si', NULL, '2025-01-24 15:22:25');

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
  `reset_token` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`user_id`, `user_name`, `user_surname`, `user_email`, `user_password`, `reset_token`, `created_at`, `updated_at`, `role_id`) VALUES
(22, 'Lorenzo', 'Castiello', 'lorenzo.castiello04@gmail.com', '$2a$10$7NDQmsRQn0Apim4VdiEM8uwxQkEsQJuE/yh7/oQ3oEtE9boq0AwNW', NULL, '2025-01-08 16:26:12', '2025-01-25 14:42:26', 1),
(23, 'Mario', 'Rossi', 'mario.rossi@gmail.com', '$2a$10$2K/0cnTyOobLwKf.fu6A5eCPAr5./.SepdWYeET3./ajLRXDMcrMG', NULL, '2025-01-08 23:51:19', '2025-01-08 23:51:19', 2),
(29, 'Stefano', 'Cherio', 'stefano.cherio@edu.itspiemonte.it', '$2a$10$6iQdwT51RoreFm6nY9c9VOZLAkTbsElDFgFykg/SBsQLqLkk78KpW', NULL, '2025-01-09 21:38:00', '2025-01-09 21:38:00', 2),
(32, 'Prova', 'Sito', 'provasito@example.com', '$2a$10$PBLWfsrNiWQGOXbBRpvdQOc4AuxrTj0HYL0SSvysPO7tjzuDX3JKq', NULL, '2025-01-13 21:19:57', '2025-01-13 21:19:57', 2),
(33, 'Giovanni', 'La Faietta', 'Giovanni.lafaietta@gmail.com', '$2a$10$3k45LZ/Ae6v3qKFW4EXO0eFrZYwFKZvH3X033A/Ik4RvhmGZsF0Ju', NULL, '2025-01-14 14:31:15', '2025-01-14 14:31:15', 2),
(34, 'Alessio', 'Suppa', 'suppaalessio1@gmail.com', '$2a$10$InrASOAAbX62fgwmc7fAOOeU/XTMrq1jODKlHYFEjZdGcjc8mhRIK', NULL, '2025-01-14 15:25:36', '2025-01-14 15:25:36', 2),
(35, 'Lorenzo', 'Castiello', 'prova@gmail.com', '$2a$10$5RTDoOVOcTX/ldmugWSygOIr7mcoCe4giYhVredp.EC89MihtJTRq', NULL, '2025-01-14 16:37:35', '2025-01-14 16:37:35', 2),
(37, 'Prova', 'Registrazione', 'prova@esempio.com', '$2a$10$lD7zhO53PMf.ZSX8NUi9JeoJm1GsiOwXMMA1dQlIQyhZ7G.Pmfa3K', NULL, '2025-01-22 16:33:28', '2025-01-22 16:33:28', 2);

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
  MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT per la tabella `feedbacks`
--
ALTER TABLE `feedbacks`
  MODIFY `feedback_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT per la tabella `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

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
