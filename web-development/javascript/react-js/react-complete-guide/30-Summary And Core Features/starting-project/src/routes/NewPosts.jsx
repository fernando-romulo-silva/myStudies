import { useState } from 'react';
import classes from './NewPost.module.css';
import Modal from '../components/Modal';

import { Link } from 'react-router-dom';

function NewPost({ onAddPost }) {
  const [enteredBody, setEnteredBody] = useState('');
  const [enteredAuthor, setEnteredAuthor] = useState('');

  function onBodyChangeHandler(event) {
    setEnteredBody(event.target.value);
  }

  function onAuthorChangeHandler(event) {
    setEnteredAuthor(event.target.value);
  }

  function submitHandler(event) {
    event.preventDefault();

    const postData = {
      body: enteredBody,
      author: enteredAuthor,
    };

    console.log(postData);

    onAddPost(postData);
    onCancel();
  }

  return (
    <Modal>
      <form className={classes.form}>
        <p>
          <label htmlFor="body">Text</label>
          <textarea id="body" required rows={3} onChange={onBodyChangeHandler} />
        </p>
        <p>
          <label htmlFor="name">Your name</label>
          <input type="text" id="name" required onChange={onAuthorChangeHandler} />
        </p>
        <p className={classes.actions}>
          <Link to=".." type="button">
            Cancel
          </Link>
          <button type="button" onClick={submitHandler}>
            Submit
          </button>
        </p>
      </form>
    </Modal>
  );
}

export default NewPost;
