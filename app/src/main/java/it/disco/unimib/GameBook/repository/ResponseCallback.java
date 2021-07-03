package it.disco.unimib.GameBook.repository;
import java.util.List;

import it.disco.unimib.GameBook.models.VideoGame;

public interface ResponseCallback {

    void onResponse(List<VideoGame> VideoGameList);
    void onFailure(String msg);


}
