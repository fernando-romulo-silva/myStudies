const addMovieBtn = document.getElementById('add-movie-btn');
const searchBtn = document.getElementById('search-btn');

const movies = [];

const renderMovies = (filter = '') => {
    const moviesList = document.getElementById('movie-list');
   
    if (movies.length === 0) {
        moviesList.classList.remove('visible');
    } else {
        moviesList.classList.add('visible');
    }

    moviesList.innerHTML = '';

    const filteredMoviees = !filter 
        ? movies 
        : movies.filter(movie => movie.info.title.includes(filter));
       
        
    filteredMoviees.forEach((movie) => {
        const movieEl = document.createElement('li');

        if (!('info' in movie)) {
            console.log('info property do not exists');
        }

        const { info, ...otherProps } = movie;

        console.log(otherProps);
      
        const { title: movieTitle } = info;

        let { getFormattedTitle } = movie;
        // getFormattedTitle = getFormattedTitle.bind(movie);

        let text = getFormattedTitle.apply(movie) + ' - ' ;   // call

        for (const key in info) {
            if (key !== 'title' && key !== '_title') {
                text = text + `${key}: ${info[key]}`;
            }
        }

        movieEl.textContent = text;
        moviesList.append(movieEl);
    });

};

const addMovieHandler = () => {
    const title = document.getElementById('title').value;
    const extraName = document.getElementById('extra-name').value;
    const extraValue = document.getElementById('extra-value').value;

    if (
        title.trim() === '' ||
        extraName.trim() === '' ||
        extraValue.trim() === ''
        ) {
        return;
    }

    const newMovie = {
        info: {
            // title,
            set title (val) {
                
                if (val.trim() === '') {
                    this._title = 'DEFAULT';
                    return;
                }

                this._title = val;
            },
            
            get title () {
                return this._title;
            },

            [extraName]: extraValue
        },
        id: Math.random().toString(),

        getFormattedTitle() {
            return this.info.title.toUpperCase()
        }
    };

    newMovie.info.title = title;
    movies.push(newMovie);
    renderMovies();
};


const searchMovieHandler = () => {
    const filterTerm = document.getElementById('filter-title').value;
    renderMovies(filterTerm);
};

addMovieBtn.addEventListener('click', addMovieHandler);

searchBtn.addEventListener('click', searchMovieHandler);
