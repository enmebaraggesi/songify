package com.songify.domain.crud;

class SongifyCrudFacadeConfiguration {
    
    public static SongifyCrudFacade createSongifyCrud(final SongRepository songRepository,
                                                      final GenreRepository genreRepository,
                                                      final ArtistRepository artistRepository,
                                                      final AlbumRepository albumRepository) {
        SongRetriever songRetriever = new SongRetriever(songRepository);
        SongUpdater songUpdater = new SongUpdater(songRepository, songRetriever);
        AlbumAdder albumAdder = new AlbumAdder(songRetriever, albumRepository);
        ArtistRetriever artistRetriever = new ArtistRetriever(artistRepository);
        AlbumRetriever albumRetriever = new AlbumRetriever(albumRepository);
        SongDeleter songDeleter = new SongDeleter(songRepository, songRetriever);
        GenreRetriever genreRetriever = new GenreRetriever(genreRepository);
        SongAdder songAdder = new SongAdder(songRepository, genreRetriever);
        ArtistAdder artistAdder = new ArtistAdder(artistRepository, genreRetriever);
        GenreAdder genreAdder = new GenreAdder(genreRepository);
        AlbumDeleter albumDeleter = new AlbumDeleter(albumRepository);
        ArtistDeleter artistDeleter = new ArtistDeleter(artistRepository, artistRetriever, albumRetriever, albumDeleter, songDeleter);
        ArtistAssigner artistAssigner = new ArtistAssigner(artistRetriever, albumRetriever);
        ArtistUpdater artistUpdater = new ArtistUpdater(artistRetriever);
        return new SongifyCrudFacade(
                songAdder,
                songRetriever,
                songDeleter,
                songUpdater,
                artistAdder,
                artistRetriever,
                artistDeleter,
                artistAssigner,
                artistUpdater,
                genreAdder,
                albumAdder,
                albumRetriever,
                genreRetriever
        );
    }
}
