import './CoreConcept.css';

// function CoreConcept(props) {
//  props.image
export default function CoreConcept({ title, description, image }) {
  return (
    <li>
      <img src={image} />
      <h3>{title}</h3>
      <p>{description}</p>
    </li>
  );
}
