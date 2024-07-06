SONGIFY: APLIKACJA DO ZARZĄDZANIA PIOSENKAMI, ARTYSTAMI I ALBUMAMI

1. ~~można dodawać artystę (nazwa artysty)~~
2. ~~można dodawać gatunek muzyczny (nazwa gatunku)~~
3. ~~można dodawać album (tytuł, data wydania, musi być w nim min. 1 piosenka)~~
4. można dodawać piosenkę (tytuł, czas trwania, data wydania, artysta do którego należy)
5. ~~można usunąć artystę (usuwają się wówczas jego piosenki i albumy)~~
6. można usunąć gatunek (nie może istnieć żadna piosenka przypisana do gatunku)
7. można usunąć album (nie może istnieć żadna piosenka przypisana do albumu)
8. ~~można usunąć piosenkę~~
9. ~~można edytować nazwę artysty~~
10. można edytować nazwę gatunku
11. można edytować zawartość, artystów, nazwę albumu
12. można edytować czas trwania, artystę, nazwę piosenki
13. można przypisać piosenki do albumów
14. ~~można przypisać artystów do albumów~~
15. można przypisać jeden gatunek muzyczny do piosenki
16. można wyświetlać wszystkie piosenki
17. można wyświetlać wszystkie gatunki
18. ~~można wyświetlać wszystkich artystów~~
19. można wyświetlać wszystkie albumy
20. ~~można wyświetlać wszystkie albumy z artystami oraz piosenkami~~
21. można wyświetlać konkretne gatunki z piosenkami
22. można wyświetlać konkretnych artystów z albumami

HAPPY PATH (user tworzy album "Eminema" z piosenkami "Till I Collapse", "Lose Yourself" o gatunku "Rap")
given there is no songs, artists, albums and genres created before
1. when I go to /song then I can see no songs
2. when I post to /song with Song "Till I Collapse" then Song "Till I Collapse" is returned with ID 1
3. when I post to /song with Song "Lose Yourself" then Song "Lose Yourself" is returned with ID 2
4. when I go to /genre then I can see no genres
5. ~~when I post to /genre with Genre "Rap" then Genre "Rap" is returned with ID 1~~
6. when I go to /song/1 then I can see default genre
7. when I put to /song/1/genre/1 then Genre with ID 1 ("Rap") is added to Song with ID 1 ("Till I Collapse")
8. when I go to /song/1 then I can see "Rap" genre
9. when I put to /song/2/genre/1 then Genre with ID 1 ("Rap") is added to Song with ID 2 ("Lose Yourself")
10. when I go to /album then I see no albums
11. ~~when I post to /album with Album "EminemAlbum1" and Song with ID 1 then Album "EminemAlbum1" is returned with ID 1~~
12. when I go to /album/1 then I can see song with ID 1 added to album
13. when I put to /album/1/song/1 then Song with ID 1 ("Till I Collapse") is added to Album with ID 1 ("EminemAlbum1")
14. when I put to /album/1/song/2 then Song with ID 2 ("Lose Yourself") is added to Album with ID 1 ("EminemAlbum1")
15. when I go to /album/1/song then I can see 2 songs (id1, id2)
16. ~~when I post to /artist with Artist "Eminem" then Artist "Eminem" is returned with ID 1~~
17. when I put to /album/1/artist/1 then Artist with ID 1 ("Eminem") is added to Album with ID 1 ("EminemAlbum1")
