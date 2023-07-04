import React from 'react';
import { Box, CircularProgress, Typography, Button, Card, CardContent } from '@mui/material';
import "./List.scss"
import { deleteBlockOrBooking } from '../helpers/api';

interface Item {
  id: number;
  startDate: string;
  endDate: string;
}

interface ListProps {
  data: Item[] | null;
  loading: boolean;
  error: Error | null;
  refetch: () => void;
  endpoint: string;
}

const List: React.FC<ListProps> = ({ data, loading, error, refetch, endpoint }) => {
  if (loading) return <CircularProgress />;
  if (error) return <p>Error: {error.message}</p>;
  if (!data) return null;

  const handleDelete = async (id: number) => {
    try {
      await deleteBlockOrBooking(endpoint, String(id));
      refetch();
    } catch (error) {
      console.error("Error deleting item:", error);
    }
  };

  return (
      <Box className="list-container">
        <Typography variant="h6">List</Typography>
        {data.length > 0 ? (
            data.map(({ id, startDate, endDate }) => (
                <Card key={id} className="list-item">
                  <CardContent>
                    <Typography className="list-item-text">
                      ID: {id}
                    </Typography>
                    <Typography className="list-item-text">
                      Start Date: {startDate}
                    </Typography>
                    <Typography className="list-item-text">
                      End Date: {endDate}
                    </Typography>
                    <Button
                        variant="contained"
                        color="secondary"
                        onClick={() => handleDelete(id)}
                    >
                      Delete
                    </Button>
                  </CardContent>
                </Card>
            ))
        ) : (
            <Typography variant="body1">Create bookings to see them here!</Typography>
        )}
      </Box>
  );
};

export default List;
