-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: music_store_recommendations
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `album_recommendation`
--

DROP TABLE IF EXISTS `album_recommendation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `album_recommendation` (
  `album_recommendation_id` int NOT NULL AUTO_INCREMENT,
  `album_id` int NOT NULL,
  `user_id` int NOT NULL,
  `liked` tinyint(1) NOT NULL,
  PRIMARY KEY (`album_recommendation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album_recommendation`
--

LOCK TABLES `album_recommendation` WRITE;
/*!40000 ALTER TABLE `album_recommendation` DISABLE KEYS */;
/*!40000 ALTER TABLE `album_recommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artist_recommendation`
--

DROP TABLE IF EXISTS `artist_recommendation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artist_recommendation` (
  `artist_recommendation_id` int NOT NULL AUTO_INCREMENT,
  `artist_id` int NOT NULL,
  `user_id` int NOT NULL,
  `liked` tinyint(1) NOT NULL,
  PRIMARY KEY (`artist_recommendation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist_recommendation`
--

LOCK TABLES `artist_recommendation` WRITE;
/*!40000 ALTER TABLE `artist_recommendation` DISABLE KEYS */;
/*!40000 ALTER TABLE `artist_recommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `label_recommendation`
--

DROP TABLE IF EXISTS `label_recommendation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `label_recommendation` (
  `label_recommendation_id` int NOT NULL AUTO_INCREMENT,
  `label_id` int NOT NULL,
  `user_id` int NOT NULL,
  `liked` tinyint(1) NOT NULL,
  PRIMARY KEY (`label_recommendation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `label_recommendation`
--

LOCK TABLES `label_recommendation` WRITE;
/*!40000 ALTER TABLE `label_recommendation` DISABLE KEYS */;
INSERT INTO `label_recommendation` VALUES (6,1,2,1);
/*!40000 ALTER TABLE `label_recommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `track_recommendation`
--

DROP TABLE IF EXISTS `track_recommendation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `track_recommendation` (
  `track_recommendation_id` int NOT NULL AUTO_INCREMENT,
  `track_id` int NOT NULL,
  `user_id` int NOT NULL,
  `liked` tinyint(1) NOT NULL,
  PRIMARY KEY (`track_recommendation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `track_recommendation`
--

LOCK TABLES `track_recommendation` WRITE;
/*!40000 ALTER TABLE `track_recommendation` DISABLE KEYS */;
INSERT INTO `track_recommendation` VALUES (6,1,2,1);
/*!40000 ALTER TABLE `track_recommendation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-09 15:20:06
