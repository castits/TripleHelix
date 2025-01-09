-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Gen 09, 2025 alle 21:29
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
  `request_id` int(11) NOT NULL,
  `participant_quantity` int(3) NOT NULL,
  `appointment_date` date NOT NULL,
  `time_slot` enum('MORNING','AFTERNOON','FULL_DAY') NOT NULL,
  `status` enum('PENDING','CONFIRMED','REFUSED') NOT NULL,
  `booking_info_req` text DEFAULT NULL,
  `reminder_sent` tinyint(1) NOT NULL,
  `feedback_sent` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `bookings`
--

INSERT INTO `bookings` (`booking_id`, `request_id`, `participant_quantity`, `appointment_date`, `time_slot`, `status`, `booking_info_req`, `reminder_sent`, `feedback_sent`) VALUES
(5, 5, 40, '2025-01-02', 'FULL_DAY', 'PENDING', NULL, 0, 1),
(6, 6, 100, '2025-01-10', 'MORNING', 'PENDING', NULL, 0, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `feedbacks`
--

CREATE TABLE `feedbacks` (
  `feedback_id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `which_lab` varchar(255) NOT NULL,
  `formative` int(11) NOT NULL,
  `engaging` int(11) NOT NULL,
  `staff_quality` int(11) NOT NULL,
  `recommend_lab` varchar(5) NOT NULL,
  `advices` text NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `information_request`
--

CREATE TABLE `information_request` (
  `information_request_id` int(11) NOT NULL,
  `request_id` int(11) NOT NULL,
  `information_request_text` text NOT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `user_surname` varchar(100) DEFAULT NULL,
  `user_email` varchar(150) NOT NULL,
  `user_phone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `requests`
--

CREATE TABLE `requests` (
  `request_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `institute` varchar(70) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `requests`
--

INSERT INTO `requests` (`request_id`, `user_id`, `institute`, `created_at`, `updated_at`) VALUES
(5, 22, 'ITS ICT', '2025-01-08 19:15:14', '2025-01-08 19:15:14'),
(6, 22, 'Scuola Torino', '2025-01-09 20:41:07', '2025-01-09 20:41:07');

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
(22, 'Lorenzo', 'Castiello', 'lorenzo.castiello04@gmail.com', '$2a$10$acvAP8GFFJaZO859z8LRI.heiFt1PoL9rN1Ui/wlFCF2XFBnegfAe', '2025-01-08 16:26:12', '2025-01-09 02:18:47', 1),
(23, 'Mario', 'Rossi', 'mario.rossi@gmail.com', '$2a$10$2K/0cnTyOobLwKf.fu6A5eCPAr5./.SepdWYeET3./ajLRXDMcrMG', '2025-01-08 23:51:19', '2025-01-08 23:51:19', 2);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`booking_id`),
  ADD KEY `fk_booking_request` (`request_id`);

--
-- Indici per le tabelle `feedbacks`
--
ALTER TABLE `feedbacks`
  ADD PRIMARY KEY (`feedback_id`),
  ADD KEY `fk_feedback_booking` (`booking_id`);

--
-- Indici per le tabelle `information_request`
--
ALTER TABLE `information_request`
  ADD PRIMARY KEY (`information_request_id`),
  ADD UNIQUE KEY `user_email` (`user_email`),
  ADD KEY `fk_info_request` (`request_id`);

--
-- Indici per le tabelle `requests`
--
ALTER TABLE `requests`
  ADD PRIMARY KEY (`request_id`),
  ADD KEY `fk_request_user` (`user_id`);

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
  MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT per la tabella `feedbacks`
--
ALTER TABLE `feedbacks`
  MODIFY `feedback_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `information_request`
--
ALTER TABLE `information_request`
  MODIFY `information_request_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `requests`
--
ALTER TABLE `requests`
  MODIFY `request_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT per la tabella `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `fk_booking_request` FOREIGN KEY (`request_id`) REFERENCES `requests` (`request_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `feedbacks`
--
ALTER TABLE `feedbacks`
  ADD CONSTRAINT `fk_feedback_booking` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `information_request`
--
ALTER TABLE `information_request`
  ADD CONSTRAINT `fk_info_request` FOREIGN KEY (`request_id`) REFERENCES `requests` (`request_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `requests`
--
ALTER TABLE `requests`
  ADD CONSTRAINT `fk_request_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
