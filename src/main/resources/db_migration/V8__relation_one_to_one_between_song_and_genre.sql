ALTER TABLE SONG
    ADD genre_id BIGINT,
    ADD CONSTRAINT FK_SONG_ON_GENRE FOREIGN KEY (GENRE_ID) REFERENCES GENRE (ID);