import { Link } from 'react-router-dom';
import classes from './Post.module.css';

function Post({ id, author, body }) {
  return (
    <div className={classes.post}>
      <Link to={id}>
        <p className={classes.author}>{author}</p>
        <p className={classes.text}>{body}</p>
      </Link>
    </div>
  );
}

export default Post;
