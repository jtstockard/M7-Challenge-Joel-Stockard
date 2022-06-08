package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackRepository extends JpaRepository <Track, Integer>{

    List<Track> findAllTracksByAlbumId(int albumId);
}
