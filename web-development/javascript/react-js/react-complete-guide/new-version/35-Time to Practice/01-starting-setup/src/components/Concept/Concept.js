export default function Concept({ concept }) {
  return (
    <li className="concept" key={concept.id}>
      <img src={concept.image} alt={concept.title} />
      <h2>{concept.title}</h2>
      <p>{concept.description}</p>
    </li>
  );
}
