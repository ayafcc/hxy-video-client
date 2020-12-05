package jaygoo.library.m3u8downloader.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import jaygoo.library.m3u8downloader.db.table.M3u8DoneInfo;


/**
 * Created by HuangYong on 2018/6/27.
 */
@Dao
public interface DoneDao {

    @Insert
    void insert(M3u8DoneInfo doneInfo);

    @Delete
    void delete(M3u8DoneInfo doneInfo);

    @Update
    void update(M3u8DoneInfo doneInfo);

    @Query("SELECT * FROM T_M3U8_DONE")
    List<M3u8DoneInfo> getAll();

    @Query("SELECT * FROM T_M3U8_DONE WHERE task_id=:taskId ")
    List<M3u8DoneInfo> getById(String taskId);
}
