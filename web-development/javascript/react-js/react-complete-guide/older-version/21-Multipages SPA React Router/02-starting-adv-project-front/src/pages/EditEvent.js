import { useRouteLoaderData } from 'react-router-dom';
import EventForm from '../components/EventForm';

function EditEventPages() {
  const data = useRouteLoaderData('event-detail');

  return <EventForm event={data.event} method="patch" />;
}

export default EditEventPages;
